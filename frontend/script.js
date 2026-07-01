const API = "http://localhost:8080/api";
let productos = [];
let eventos = [];

function irA(id){document.getElementById(id).scrollIntoView({behavior:"smooth"});}
function dinero(v){return new Intl.NumberFormat("es-CL",{style:"currency",currency:"CLP",maximumFractionDigits:0}).format(v||0);}
function valor(id){const n=parseInt(document.getElementById(id).value);return isNaN(n)?0:n;}
function texto(id){return document.getElementById(id).value.trim();}

function datosMinuta(){
  return {
    asistentes:valor("asistentes"),
    menuCarne:valor("menuCarne"), menuPollo:valor("menuPollo"), menuVegetariano:valor("menuVegetariano"), menuVegano:valor("menuVegano"), menuNinos:valor("menuNinos"),
    canapes:valor("canapes"), crostinis:valor("crostinis"), tapaditos:valor("tapaditos"), rollitosSalmon:valor("rollitosSalmon"), rollitosJamon:valor("rollitosJamon"), brochetasGourmet:valor("brochetasGourmet"),
    empanaditas:valor("empanaditas"), pizzetas:valor("pizzetas"), miniQuiche:valor("miniQuiche"), brochetasPollo:valor("brochetasPollo"),
    shotsPostre:valor("shotsPostre"), brownies:valor("brownies"), alfajores:valor("alfajores"), brochetasFruta:valor("brochetasFruta"),
    jugoLitros:valor("jugoLitros"), aguas:valor("aguas"), piscoSour:valor("piscoSour"), espumante:valor("espumante")
  };
}

async function generarMinuta(){
  try{
    const res=await fetch(API+"/minuta",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(datosMinuta())});
    if(!res.ok) throw new Error("error");
    const data=await res.json();
    pintarResultado(data);
  }catch(e){alert("Error al generar minuta. Revisa que el backend esté encendido.");console.error(e);}
}

function pintarResultado(data){
  document.getElementById("resultado").innerHTML=`
    <div class="total"><span>Total estimado referencial</span><strong>${dinero(data.totalEstimado)}</strong></div>
    <p><b>Asistentes:</b> ${data.asistentes}</p>
    <p><b>Adultos:</b> ${data.adultos}</p>
    ${bloque("Productos seleccionados",data.productosSeleccionados)}
    ${bloque("Insumos aproximados",data.insumos)}
    ${bloque("Menú infantil",data.menuInfantil)}
    ${bloque("Bebidas",data.bebidas)}
    ${bloque("Personal requerido",data.personal)}
    ${bloqueLista("Tareas operativas",data.tareas)}
    <p class="muted">${data.nota}</p>`;
}

function bloque(titulo,obj){
  let html="";
  for(const k in obj){html+=`<li><b>${k}:</b> ${obj[k]}</li>`;}
  return `<div class="result-block"><h4>${titulo}</h4><ul>${html}</ul></div>`;
}
function bloqueLista(titulo,lista){
  let html="";
  for(const item of lista){html+=`<li>${item}</li>`;}
  return `<div class="result-block"><h4>${titulo}</h4><ul>${html}</ul></div>`;
}

async function registrarEvento(){
  if(!texto("nombreEvento") || !texto("fechaEvento")){alert("Completa nombre y fecha del evento.");return;}
  const d=datosMinuta();
  const obs=
    "Lugar: "+texto("lugarEvento")+"\n"+
    "Menús carne: "+d.menuCarne+", pollo: "+d.menuPollo+", vegetariano: "+d.menuVegetariano+", vegano: "+d.menuVegano+", niños: "+d.menuNinos+"\n"+
    "Fríos: canapés "+d.canapes+", crostinis "+d.crostinis+", tapaditos "+d.tapaditos+"\n"+
    "Calientes: empanaditas "+d.empanaditas+", pizzetas "+d.pizzetas+", mini quiche "+d.miniQuiche+"\n"+
    "Dulces/bebidas: postres "+d.shotsPostre+", jugos litros "+d.jugoLitros+", aguas "+d.aguas+"\n"+
    "Observaciones: "+texto("observaciones");

  const evento={nombreEvento:texto("nombreEvento"),tipoEvento:texto("tipoEvento"),fecha:texto("fechaEvento"),asistentes:d.asistentes,estado:"pendiente",observaciones:obs};

  try{
    const res=await fetch(API+"/eventos",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(evento)});
    if(!res.ok) throw new Error("error");
    alert("Evento registrado correctamente");
    await cargarEventos();
    irA("admin");
  }catch(e){alert("Error al registrar evento.");console.error(e);}
}

async function cargarProductos(){
  try{
    const res=await fetch(API+"/productos");
    productos=await res.json();
    document.getElementById("catalogoProductos").innerHTML=productos.map(p=>`
      <div class="product"><span>${p.categoria}</span><h3>${p.nombre}</h3><p>${p.descripcion||""}</p><strong>${dinero(p.precio)}</strong></div>
    `).join("");
    pintarTablaProductos();
    actualizarResumen();
  }catch(e){document.getElementById("catalogoProductos").innerHTML="<p>No se pudieron cargar productos.</p>";}
}

function pintarTablaProductos(){
  document.getElementById("tablaProductos").innerHTML=`
    <table><thead><tr><th>ID</th><th>Producto</th><th>Categoría</th><th>Precio</th><th>Descripción</th></tr></thead>
    <tbody>${productos.map(p=>`<tr><td>${p.idProducto}</td><td>${p.nombre}</td><td>${p.categoria}</td><td>${dinero(p.precio)}</td><td>${p.descripcion||""}</td></tr>`).join("")}</tbody></table>`;
}

async function crearProducto(){
  if(!texto("nombreProducto") || !texto("categoriaProducto") || !valor("precioProducto")){alert("Completa nombre, categoría y precio.");return;}
  const producto={nombre:texto("nombreProducto"),categoria:texto("categoriaProducto"),precio:valor("precioProducto"),descripcion:texto("descripcionProducto")};
  try{
    const res=await fetch(API+"/productos",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(producto)});
    if(!res.ok) throw new Error("error");
    alert("Producto guardado correctamente");
    document.getElementById("nombreProducto").value="";
    document.getElementById("categoriaProducto").value="";
    document.getElementById("precioProducto").value="";
    document.getElementById("descripcionProducto").value="";
    await cargarProductos();
  }catch(e){alert("Error al crear producto.");console.error(e);}
}

async function cargarEventos(){
  try{
    const res=await fetch(API+"/eventos");
    eventos=await res.json();
    document.getElementById("tablaEventos").innerHTML=`
      <table><thead><tr><th>Evento</th><th>Tipo</th><th>Fecha</th><th>Asistentes</th><th>Estado</th><th>Actualizar</th></tr></thead>
      <tbody>${eventos.map(e=>`
        <tr>
          <td><b>${e.nombreEvento}</b><br><span class="muted">${e.observaciones||""}</span></td>
          <td>${e.tipoEvento}</td><td>${e.fecha}</td><td>${e.asistentes||0}</td><td>${badge(e.estado)}</td>
          <td><div class="inline"><select class="estado" id="estado-${e.idEvento}">
            <option value="pendiente" ${(e.estado||"").toLowerCase()=="pendiente"?"selected":""}>Pendiente</option>
            <option value="confirmado" ${(e.estado||"").toLowerCase()=="confirmado"?"selected":""}>Confirmado</option>
            <option value="cancelado" ${(e.estado||"").toLowerCase()=="cancelado"?"selected":""}>Cancelado</option>
          </select><button class="small" onclick="actualizarEstado(${e.idEvento})">Guardar</button></div></td>
        </tr>`).join("")}</tbody></table>`;
    actualizarResumen();
  }catch(e){document.getElementById("tablaEventos").innerHTML="<p>No se pudieron cargar eventos.</p>";}
}

function badge(estado){
  const e=(estado||"pendiente").toLowerCase();
  if(e==="confirmado") return `<span class="badge confirmado">Confirmado</span>`;
  if(e==="cancelado") return `<span class="badge cancelado">Cancelado</span>`;
  return `<span class="badge pendiente">Pendiente</span>`;
}

async function actualizarEstado(id){
  const evento=eventos.find(e=>e.idEvento===id);
  if(!evento) return;
  evento.estado=document.getElementById("estado-"+id).value;
  try{
    const res=await fetch(API+"/eventos/"+id,{method:"PUT",headers:{"Content-Type":"application/json"},body:JSON.stringify(evento)});
    if(!res.ok) throw new Error("error");
    alert("Estado actualizado");
    await cargarEventos();
  }catch(e){alert("Error al actualizar estado.");console.error(e);}
}

function actualizarResumen(){
  document.getElementById("totalEventos").textContent=eventos.length;
  document.getElementById("totalConfirmados").textContent=eventos.filter(e=>(e.estado||"").toLowerCase()==="confirmado").length;
  document.getElementById("totalProductos").textContent=productos.length;
  document.getElementById("totalAsistentes").textContent=eventos.reduce((s,e)=>s+(e.asistentes||0),0);
}

cargarProductos();
cargarEventos();
