package it.uniroma3.domain.repository


import it.uniroma3.domain.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User,Long>{
    fun findByUsername(username:String) : User
}