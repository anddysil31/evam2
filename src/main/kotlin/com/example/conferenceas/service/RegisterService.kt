package com.example.conferenceas.service

import com.example.conferenceas.model.Conference
import com.example.conferenceas.model.Register
import com.example.conferenceas.repository.ConferenceRepository
import com.example.conferenceas.repository.RegisterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import kotlin.random.Random

@Service
class RegisterService {
    @Autowired
    lateinit var registerRepository: RegisterRepository
    @Autowired
    lateinit var conferenceRepository:ConferenceRepository

    fun list(): List<Register> {
        return registerRepository.findAll()
    }
    fun list(pageable: Pageable, register: Register): Page<Register> {
        val matcher = ExampleMatcher.matching()
            .withIgnoreNullValues()
            .withMatcher(("registeredAt"), ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
        return registerRepository.findAll(Example.of(register, matcher), pageable)
    }

    fun listById(id:Long?):Register?{
        return registerRepository.findById(id)
    }

    fun listConferences(mid: Long?):Any? {
        return registerRepository.allConferences(mid)
    }
    fun delete(id: Long?):Boolean?{
        registerRepository.findById(id)?:
        throw Exception()
        registerRepository.deleteById(id!!)
        return true
    }
    fun save(register: Register): Register {
        val response = registerRepository.save(register)
        updateCode(response)
        updateAssisted(response)
        return response
    }

    fun update(register: Register): Register {
        try {
            registerRepository.findById(register.id)
                ?: throw Exception("El id ${register.id} en registere no existe")
            return registerRepository.save(register)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun updateCode (register: Register){
        val arr = IntArray(5) { Random.nextInt(1,9) }
        val gencode = arr.joinToString ("-")
        val registerResponse = registerRepository.findById(register.id)
        registerResponse?.apply{
            code = gencode
        }
    registerRepository.save(registerResponse!!)
    }

    fun updateAssisted(register: Register):Register{
        try {
            val registerResponse = registerRepository.findById(register.id)
                ?:throw Exception("El ${register.id} en registro no existe")
            return registerRepository.save(register)
            val conferenceResponse = conferenceRepository.findById(register.conferenceId)
            registerResponse?.apply {
                assisted = register.assisted
                registerRepository.save(registerResponse!!)
                if (assisted == true) {
                    conferenceResponse?.apply {
                        totalAttendees = totalAttendees?.plus(1)
                    }
                    conferenceRepository.save(conferenceResponse!!)

                }


            }

        }catch (ex:Exception){
            throw  ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
        //        conferenceResponse?.apply{
//            totalAttendees = totalAttendees?.plus(1)
//        }
//        conferenceRepository.save(conferenceResponse!!)
    }


//}
}