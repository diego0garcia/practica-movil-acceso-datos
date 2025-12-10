package ies.sequeros.com.dam.pmdm.administrador.infraestructura.dependientes

import ies.sequeros.com.dam.pmdm.administrador.aplicacion.pedidos.crear.IEncryptador
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.jasypt.salt.FixedStringSaltGenerator

class Encryptador: IEncryptador {

    private val ALGORITHM = "PBEWithMD5AndDES"
    private val PASSWORD = "1234"
    private val ITERATIONS = 1000
    private val FIXED_SALT = "salt"

    override fun encriptar(text: String): String {
        val encryptor: StandardPBEStringEncryptor = StandardPBEStringEncryptor()

        encryptor.setAlgorithm(ALGORITHM)
        encryptor.setPassword(PASSWORD)

        encryptor.setKeyObtentionIterations(ITERATIONS)

        val saltGenerator = FixedStringSaltGenerator()

        saltGenerator.setSalt(FIXED_SALT)

        encryptor.setSaltGenerator(saltGenerator)


        val myEncryptedPassword: String = encryptor.encrypt(text)

        return myEncryptedPassword
    }
}