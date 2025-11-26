package ies.sequeros.com.dam.pmdm.administrador.aplicacion.productos.listar

import ies.sequeros.com.dam.pmdm.administrador.modelo.Producto

fun Producto.toDTO(path:String="") = ProductoDTO(
    id = id,
    categoriaId = categoriaId,
    name = name,
    imagePath = imagePath,
    description = description,
    price = price,
    enabled,
    categoriaName = categoriaName
)
fun ProductoDTO.toProducto()= Producto(
    id = id,
    categoriaId = categoriaId,
    name = name,
    imagePath = imagePath,
    description = description,
    price = price,
    enabled,
    categoriaName = categoriaName
)