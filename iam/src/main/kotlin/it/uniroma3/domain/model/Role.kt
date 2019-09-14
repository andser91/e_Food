package it.uniroma3.domain.model

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.io.Serializable
import javax.persistence.*

@Entity
data class Role (var name : String = "") : Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0

    companion object {
        private const val serialVersionUID = 1L
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        val role = other as Role
        return id == role.id
    }

    override fun toString(): String {
        return name
    }


}