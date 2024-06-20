//Obtengo valores de lo seleccionado en el select de actores
function actorSelected(select) {
	let index = select.selectedIndex;
	let option = select.options[index];
	let id = option.value;
	let nombre = option.text;
	let urlImagen = option.dataset.url;

	//Deshabilito el item option xq cuando tengamos un actor, lo va a agregar y ya no lo vamos a poder volver
	//a agregar xq ya va a estar asociado con la pelicula
	option.disabled = "disabled";

	//Hago que el combo vuelva a la posición 0 p/que nos permita seleccionar más actores
	select.selectedIndex = 0;

	agregarActor(id, nombre, urlImagen)

	//Obtenemos los ids de los actores que tengamos en el input Ids
	let ids = $("#ids").val();
	if (ids == "") { //si el id tiene comillas, es xq no tiene ninguno. Entonces va a ser el primero/lo creamos.
		$("#ids").val(id);
	} else { //en caso que ya tenga algún id, lo concatenamos
		$("#ids").val(ids + "," + id);
	}

}

function agregarActor(id, nombre, urlImagen) {
	let htmlString = `
		<div class= "card col-md-3 m-2" style= "width: 10rem">
			<img src="{URL-IMAGEN}" class= "card-img-top">
			<div class= "card-body">
				<p class="card-text">{NOMBRE}</p>
				<button class="btn btn-danger" data-id="{ID}" onclick="eliminarActor(this); return false;"> Eliminar </button>
			</div>
		</div>`;

	htmlString = htmlString.replace("{URL-IMAGEN}", urlImagen);
	htmlString = htmlString.replace("{NOMBRE}", nombre);
	htmlString = htmlString.replace("{ID}", id);

	//Una vez reemplazados los valores, lo metemos en nuestro contenedor de protagonistas
	$("#protagonistas_container").append(htmlString);
}

function eliminarActor(btn) {
	let id = btn.dataset.id;
	let node = btn.parentElement.parentElement;
	let arrayIds = $("#ids").val().split(",").filter(idActor => idActor != id);
	//1,2,3,10 => [1,2,3,10] => supongamos que es el 3 => [1,2,10]

	$("#ids").val(arrayIds.join(",")); //hacemos la operación inversa de split, con join.
	//"1,2,10"

	//Ahora volvemos a hablitar la opción del combo de actores
	$("#protagonistas option[value='" + id + "']").prop("disabled", false);

	$(node).remove();
}

/*function previsualizar() {
	let reader = new FileReader();

	reader.readAsDataURL(document.getElementById("imagen").files[0]);

	reader.onload = function(e) {
		let vistaPrevia = document.getElementById("vista_previa");

		vistaPrevia.classList.remove("d-none");
		vistaPrevia.style.backgroundImage = 'url("' + e.target.result + '")';
	}

}*/
function previsualizar() {
    let archivo = document.getElementById("archivo");
    if (archivo.files.length > 0) {
        let reader = new FileReader();
        reader.readAsDataURL(archivo.files[0]);
        reader.onload = function(e) {
            let vistaPrevia = document.getElementById("vista_previa");
            vistaPrevia.classList.remove("d-none");
            vistaPrevia.style.backgroundImage = 'url("' + e.target.result + '")';
        }
    } else {
        console.error("No se seleccionó ningún archivo.");
    }
}