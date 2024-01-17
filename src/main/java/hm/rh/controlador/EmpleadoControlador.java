package hm.rh.controlador;


import hm.rh.excepcion.RecursoNoEncontradoExepcion;
import hm.rh.modelo.Empleado;
import hm.rh.servicio.IEmpleadoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//http:localhost:8080/rh-app/
@RequestMapping("rh-app")
@CrossOrigin(value= "http://localhost:3000")
public class EmpleadoControlador {
    private static final Logger logger=
            LoggerFactory.getLogger(EmpleadoControlador.class);

    @Autowired
   private IEmpleadoServicio empleadoServicio;

    @GetMapping("/empleados")
    public List<Empleado> obtenerEmpleados(){
        var empleados = empleadoServicio.listarEmpleados();
     //  empleados.forEach((empleado->logger.info(empleado.toString())));
        return empleados;
    }

    @PostMapping("/empleados")
    public Empleado AgregarEmpleado(@RequestBody Empleado empleado){
        logger.info(empleado.toString());
        return empleadoServicio.guardarEmpleado(empleado);
    }
    @GetMapping("/empleados/{id}")
    public ResponseEntity <Empleado> ObtenerEmpleadoPorId(@PathVariable Integer id){
      Empleado empleado=  empleadoServicio.buscarEmpleadoPorId(id);
     if(empleado==null)
         throw new RecursoNoEncontradoExepcion("No se encontro un empleado que tenga el id:" + id.toString());

      return ResponseEntity.ok(empleado);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity <Empleado> actualizarEmpleado(@PathVariable Integer id, @RequestBody Empleado empleadoActualizado){
        Empleado empleado=  empleadoServicio.buscarEmpleadoPorId(id);
        if(empleado==null)
            throw new RecursoNoEncontradoExepcion("No se encontro un empleado con el id:" + id.toString());
        empleado.setNombre(empleadoActualizado.getNombre());
        empleado.setDepartamento(empleadoActualizado.getDepartamento());
        empleado.setSueldo(empleadoActualizado.getSueldo());
        empleadoServicio.guardarEmpleado(empleado);
        return ResponseEntity.ok(empleado);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String,Boolean>> eliminarEmpleado(@PathVariable Integer id){
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if(empleado==null)
            throw new RecursoNoEncontradoExepcion("No se encontro un empleado con el id:" + id.toString());
        empleadoServicio.eliminarEmpleado(empleado );
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("Eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);


    }

}
