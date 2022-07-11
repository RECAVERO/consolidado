package com.nttdata.utils.convert;

import com.nttdata.domain.models.MovementDto;
import com.nttdata.infraestructure.document.Movement;
import org.springframework.beans.BeanUtils;

public class Convert {
    public static MovementDto entityToDto(Movement movement) {
        MovementDto movementDto = new MovementDto();
        BeanUtils.copyProperties(movement, movementDto);
        return movementDto;
    }

    public static Movement dtoToEntity(MovementDto movementDto) {
        Movement movement = new Movement();
        BeanUtils.copyProperties(movementDto, movement);
        return movement;
    }
}
