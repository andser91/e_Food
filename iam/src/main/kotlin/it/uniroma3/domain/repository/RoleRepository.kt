package it.uniroma3.domain.repository


import it.uniroma3.domain.model.Role
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : CrudRepository<Role,Long>{
    fun findByName(name:String) : Role
}