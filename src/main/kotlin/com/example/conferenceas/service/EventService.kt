package com.example.conferenceas.service

import com.example.conferenceas.model.Conference
import com.example.conferenceas.model.Event
import com.example.conferenceas.repository.ConferenceRepository
import com.example.conferenceas.repository.EventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
class EventService {
    @Autowired
    lateinit var eventRepository: EventRepository


    fun list(pageable: Pageable, event: Event): Page<Event> {
        val matcher = ExampleMatcher.matching()
            .withIgnoreNullValues()
            .withMatcher(("place"), ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
        return eventRepository.findAll(Example.of(event, matcher), pageable)
    }

    fun listById(id:Long?):Event?{
        return eventRepository.findById(id)
    }
    fun delete(id:Long?):Boolean?{
        eventRepository.findById(id)?:
        throw Exception()
        eventRepository.deleteById(id!!)
        return true
    }
    fun save(event: Event): Event {
      return eventRepository.save(event)

    }

    fun update(event: Event): Event {
        try {
            eventRepository.findById(event.id)
                ?: throw Exception("El id ${event.id} en event no existe")
            return eventRepository.save(event)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }



}
