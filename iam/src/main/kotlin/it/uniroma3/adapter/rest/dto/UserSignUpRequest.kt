package it.uniroma3.adapter.rest.dto

data class UserSignUpRequest(val username : String, val email : String,
                             val password : String, val firstname : String, val lastname : String)