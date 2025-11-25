package ies.sequeros.com.dam.pmdm.administrador.aplicacion.categorias.crear



data class CrearCategoriaCommand(
                                 val name:String,
                                 val imagePath:String,
                                 val description: String,
                                 val enabled: Boolean)