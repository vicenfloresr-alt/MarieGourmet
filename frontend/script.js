const API = "http://localhost:8080/api";

let productosGlobal = [];
let eventosGlobal = [];
let categoriaActual = "Todos";

function scrollToSection(id) {
  document.getElementById(id).scrollIntoView({ behavior: "smooth" });
}

function formatoPrecio(valor) {
  return new Intl.NumberFormat("es-CL", {
    style: "currency",
    currency: "CLP",
    maximumFractionDigits: 0
  }).format(valor || 0);
}

function escapeHtml(texto) {
  if (texto === null || texto === undefined) return "";
  return String(texto)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;");
}

function obtenerNumero(id) {
  const valor = parseInt(document.getElementById(id).value);
  return isNaN(valor) ? 0 : valor;
}

function obtenerTipoServicio() {
  const seleccionado = document.querySelector("input[name='tipoServicio']:checked");
  return seleccionado ? seleccionado.value : "Coffee break";
}

function actualizarTarjetasServicio() {
  document.querySelectorAll(".service-card").forEach(card => {
    const input = card.querySelector("input");
    card.classList.toggle("selected", input.checked);
  });
}

document.querySelectorAll("input[name='tipoServicio']").forEach(input => {
  input.addEventListener("change", actualizarTarjetasServicio);
});

function obtenerDatosCotizacion() {
  return {
    nombreEvento: document.getElementById("nombreEvento").value.trim(),
    fecha: document.getElementById("fechaEvento").value,
    asistentes: obtenerNumero("asistentesEvento"),
    duracion: document.getElementById("duracionEvento").value,
    lugar: document.getElementById("lugarEvento").value.trim(),
    tipoEvento: obtenerTipoServicio(),
    vegetarianos: obtenerNumero("vegetarianos"),
    veganos: obtenerNumero("veganos"),
    sinGluten: obtenerNumero("sinGluten"),
    sinFrutosSecos: obtenerNumero("sinFrutosSecos"),
    keto: obtenerNumero("keto"),
    ninos: obtenerNumero("ninos"),
    botellasPisco: obtenerNumero("botellasPisco"),
    botellasEspumante: obtenerNumero("botellasEspumante"),
    garzonesExtra: obtenerNumero("garzonesExtra"),
    montajePremium: document.getElementById("montajePremium").checked,
    observaciones: document.getElementById("observaciones").value.trim()
  };
}

async function cotizarEvento() {
  const datos = obtenerDatosCotizacion();

  if (!datos.asistentes) {
    alert("Ingresa el número de asistentes.");
    return;
  }

  try {
    const respuesta = await fetch(API + "/cotizar", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(datos)
    });

    if (!respuesta.ok) {
      throw new Error("No se pudo calcular la cotización");
    }

    const resultado = await respuesta.json();

    const extrasHtml = resultado.extras && resultado.extras.length > 0
      ? resultado.extras.map(extra => `
          <tr>
            <td>${escapeHtml(extra.nombre)} x${extra.cantidad}</td>
            <td>${formatoPrecio(extra.subtotal)}</td>
          </tr>
        `).join("")
      : `<tr><td>Sin opciones especiales</td><td>$0</td></tr>`;

    const insumosHtml = Object.entries(resultado.insumosEstimados || {})
      .map(([nombre, cantidad]) => `<li><strong>${escapeHtml(nombre)}:</strong> ${escapeHtml(cantidad)}</li>`)
      .join("");

    document.getElementById("resultadoCotizacion").innerHTML = `
      <h3>${escapeHtml(datos.nombreEvento || "Evento sin nombre")}</h3>
      <p><strong>Servicio base:</strong> ${escapeHtml(resultado.tipoEvento)}</p>
      <p><strong>Fecha:</strong> ${escapeHtml(datos.fecha || "No definida")}</p>
      <p><strong>Lugar:</strong> ${escapeHtml(datos.lugar || "No definido")}</p>
      <p><strong>Duración:</strong> ${escapeHtml(datos.duracion)}</p>
      <p><strong>Asistentes:</strong> ${resultado.asistentes}</p>

      <div class="quote-total">
        <span>Total estimado final</span>
        <strong>${formatoPrecio(resultado.totalEstimado)}</strong>
      </div>

      <table class="summary-table">
        <tr>
          <td>Costo base</td>
          <td>${formatoPrecio(resultado.costoBase)}</td>
        </tr>
        <tr>
          <td>Opciones especiales</td>
          <td>${formatoPrecio(resultado.costoExtras)}</td>
        </tr>
      </table>

      <h4>Detalle de opciones especiales</h4>
      <table class="summary-table">
        ${extrasHtml}
      </table>

      <h4>Insumos estimados</h4>
      <ul>${insumosHtml}</ul>

      <p class="muted">${escapeHtml(resultado.recomendacion)}</p>
    `;
  } catch (error) {
    console.error(error);
    alert("Error al calcular cotización. Revisa que el backend esté encendido.");
  }
}

async function registrarEvento() {
  const datos = obtenerDatosCotizacion();

  if (!datos.nombreEvento || !datos.fecha || !datos.asistentes) {
    alert("Completa nombre, fecha y asistentes antes de registrar.");
    return;
  }

  const observaciones = `
Lugar: ${datos.lugar || "No indicado"}
Duración: ${datos.duracion}
Observaciones: ${datos.observaciones || "Sin observaciones"}
Especiales: vegetarianos ${datos.vegetarianos}, veganos ${datos.veganos}, sin gluten ${datos.sinGluten}, sin frutos secos ${datos.sinFrutosSecos}, keto ${datos.keto}, niños ${datos.ninos}, pisco sour ${datos.botellasPisco}, espumante ${datos.botellasEspumante}, garzones extra ${datos.garzonesExtra}, montaje premium ${datos.montajePremium ? "sí" : "no"}.
  `.trim();

  const evento = {
    nombreEvento: datos.nombreEvento,
    tipoEvento: datos.tipoEvento,
    fecha: datos.fecha,
    asistentes: datos.asistentes,
    estado: "pendiente",
    observaciones: observaciones
  };

  try {
    const respuesta = await fetch(API + "/eventos", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(evento)
    });

    if (!respuesta.ok) {
      throw new Error("No se pudo registrar el evento");
    }

    await respuesta.json();
    alert("Solicitud registrada correctamente");
    await cargarTodo();
    scrollToSection("admin");
  } catch (error) {
    console.error(error);
    alert("Error al registrar solicitud. Revisa que el backend esté encendido.");
  }
}

async function cargarProductos() {
  try {
    const respuesta = await fetch(API + "/productos");
    productosGlobal = await respuesta.json();

    pintarCatalogo();
    pintarTablaProductos();
  } catch (error) {
    console.error(error);
    document.getElementById("catalogoProductos").innerHTML = `
      <div class="panel">
        <p>No se pudieron cargar productos. Revisa que el backend esté corriendo.</p>
      </div>
    `;
  }
}

function pintarCatalogo() {
  const productos = categoriaActual === "Todos"
    ? productosGlobal
    : productosGlobal.filter(p => p.categoria === categoriaActual);

  document.getElementById("catalogoProductos").innerHTML = productos.map(producto => `
    <article class="product-card">
      <span class="category">${escapeHtml(producto.categoria)}</span>
      <h3>${escapeHtml(producto.nombre)}</h3>
      <p>${escapeHtml(producto.descripcion || "Producto del catálogo Marie Gourmet.")}</p>
      <strong>${formatoPrecio(producto.precio)}</strong>
    </article>
  `).join("");
}

function filtrarProductos(categoria, boton) {
  categoriaActual = categoria;

  document.querySelectorAll(".filter-btn").forEach(btn => btn.classList.remove("active"));
  boton.classList.add("active");

  pintarCatalogo();
}

function pintarTablaProductos() {
  document.getElementById("tablaProductos").innerHTML = `
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Producto</th>
          <th>Categoría</th>
          <th>Precio</th>
          <th>Descripción</th>
        </tr>
      </thead>
      <tbody>
        ${productosGlobal.map(producto => `
          <tr>
            <td>${producto.idProducto}</td>
            <td>${escapeHtml(producto.nombre)}</td>
            <td>${escapeHtml(producto.categoria)}</td>
            <td>${formatoPrecio(producto.precio)}</td>
            <td>${escapeHtml(producto.descripcion)}</td>
          </tr>
        `).join("")}
      </tbody>
    </table>
  `;
}

async function crearProducto() {
  const nombre = document.getElementById("nombreProducto").value.trim();
  const categoria = document.getElementById("categoriaProducto").value.trim();
  const precio = obtenerNumero("precioProducto");
  const descripcion = document.getElementById("descripcionProducto").value.trim();

  if (!nombre || !categoria || !precio) {
    alert("Completa nombre, categoría y precio.");
    return;
  }

  const producto = {
    nombre,
    categoria,
    precio,
    descripcion
  };

  try {
    const respuesta = await fetch(API + "/productos", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(producto)
    });

    if (!respuesta.ok) {
      throw new Error("No se pudo crear producto");
    }

    await respuesta.json();

    document.getElementById("nombreProducto").value = "";
    document.getElementById("categoriaProducto").value = "";
    document.getElementById("precioProducto").value = "";
    document.getElementById("descripcionProducto").value = "";

    alert("Producto creado correctamente");
    await cargarProductos();
    actualizarDashboard();
  } catch (error) {
    console.error(error);
    alert("Error al crear producto.");
  }
}

async function cargarEventos() {
  try {
    const respuesta = await fetch(API + "/eventos");
    eventosGlobal = await respuesta.json();

    document.getElementById("tablaEventos").innerHTML = `
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Evento</th>
            <th>Servicio</th>
            <th>Fecha</th>
            <th>Asistentes</th>
            <th>Estado</th>
            <th>Actualizar estado</th>
          </tr>
        </thead>
        <tbody>
          ${eventosGlobal.map(evento => `
            <tr>
              <td>${evento.idEvento}</td>
              <td>
                <strong>${escapeHtml(evento.nombreEvento)}</strong><br>
                <span class="muted">${escapeHtml(evento.observaciones || "")}</span>
              </td>
              <td>${escapeHtml(evento.tipoEvento)}</td>
              <td>${escapeHtml(evento.fecha)}</td>
              <td>${evento.asistentes || 0}</td>
              <td>${badgeEstado(evento.estado)}</td>
              <td>
                <div class="inline-actions">
                  <select id="estado-${evento.idEvento}" class="estado-select">
                    <option value="pendiente" ${(evento.estado || "").toLowerCase() === "pendiente" ? "selected" : ""}>Pendiente</option>
                    <option value="confirmado" ${(evento.estado || "").toLowerCase() === "confirmado" ? "selected" : ""}>Confirmado</option>
                    <option value="cancelado" ${(evento.estado || "").toLowerCase() === "cancelado" ? "selected" : ""}>Cancelado</option>
                  </select>
                  <button class="small-btn" onclick="actualizarEstadoEvento(${evento.idEvento})">Guardar</button>
                </div>
              </td>
            </tr>
          `).join("")}
        </tbody>
      </table>
    `;
  } catch (error) {
    console.error(error);
    document.getElementById("tablaEventos").innerHTML = `
      <p>No se pudieron cargar eventos. Revisa que el backend esté corriendo.</p>
    `;
  }
}

function badgeEstado(estado) {
  const valor = (estado || "pendiente").toLowerCase();

  if (valor === "confirmado") {
    return '<span class="badge badge-confirmado">Confirmado</span>';
  }

  if (valor === "cancelado") {
    return '<span class="badge badge-cancelado">Cancelado</span>';
  }

  return '<span class="badge badge-pendiente">Pendiente</span>';
}

async function actualizarEstadoEvento(idEvento) {
  const evento = eventosGlobal.find(item => item.idEvento === idEvento);

  if (!evento) {
    alert("Evento no encontrado.");
    return;
  }

  const nuevoEstado = document.getElementById("estado-" + idEvento).value;

  const datos = {
    idEvento: evento.idEvento,
    nombreEvento: evento.nombreEvento,
    tipoEvento: evento.tipoEvento,
    fecha: evento.fecha,
    asistentes: evento.asistentes,
    estado: nuevoEstado,
    observaciones: evento.observaciones
  };

  try {
    const respuesta = await fetch(API + "/eventos/" + idEvento, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(datos)
    });

    if (!respuesta.ok) {
      throw new Error("No se pudo actualizar el evento");
    }

    await respuesta.json();
    alert("Estado actualizado correctamente");
    await cargarEventos();
    actualizarDashboard();
  } catch (error) {
    console.error(error);
    alert("Error al actualizar estado.");
  }
}

function actualizarDashboard() {
  const totalEventos = eventosGlobal.length;
  const confirmados = eventosGlobal.filter(e => (e.estado || "").toLowerCase() === "confirmado").length;
  const totalProductos = productosGlobal.length;
  const totalAsistentes = eventosGlobal.reduce((acum, evento) => acum + (evento.asistentes || 0), 0);

  document.getElementById("totalEventos").textContent = totalEventos;
  document.getElementById("eventosConfirmados").textContent = confirmados;
  document.getElementById("totalProductos").textContent = totalProductos;
  document.getElementById("totalAsistentes").textContent = totalAsistentes;
}

async function cargarTodo() {
  await cargarProductos();
  await cargarEventos();
  actualizarDashboard();
}

cargarTodo();
