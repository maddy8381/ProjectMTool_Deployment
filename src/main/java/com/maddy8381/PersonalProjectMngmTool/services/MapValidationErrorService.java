package com.maddy8381.PersonalProjectMngmTool.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapValidationErrorService {

    public ResponseEntity<?> MapValidationService(BindingResult result){
        //@Valid for good error message if invalid data
        //BindingResult is an Interface tht invokes validator on an object.

        if(result.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            //Getting errorField and Default msg from result and returning back
            for(FieldError error: result.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<Map<String, String>>( errorMap, HttpStatus.BAD_REQUEST);
        }

        return null;
    }
}
