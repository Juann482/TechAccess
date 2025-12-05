function confirmarEliminacion(id) {
    Swal.fire({
        title: "¿Estás seguro?",
        text: "El usuario se eliminará permanentemente",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Eliminar",
        cancelButtonText: "Cancelar"
    }).then((result) => {
        if (result.isConfirmed) {
            // Redirige al controlador
            window.location.href = "/Administrador/deleteUser/" + id;
        }
    });
}

function confirmarEliminacionFicha(id) {
    Swal.fire({
        title: "¿Estás seguro?",
        text: "La ficha se eliminará permanentemente",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Eliminar",
        cancelButtonText: "Cancelar"
    }).then((result) => {
        if (result.isConfirmed) {
            // Redirige al controlador
            window.location.href = "/Administrador/delete/" + id;
        }
    });
}