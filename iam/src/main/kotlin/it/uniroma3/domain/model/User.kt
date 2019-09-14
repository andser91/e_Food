package it.uniroma3.domain.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "\"user\"")
data class User (var firstname: String = "",
                 var lastname: String = "",
                 var username: String = "",
                 var email: String = "",
                 var password: String = "") : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var accountExpired: Boolean = false
    var accountLocked: Boolean = false
    var credentialsExpired: Boolean = false
    var enabled: Boolean = true
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var roles: Set<Role> = HashSet()


    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        val user = other as User
        return id == user.id
    }


}