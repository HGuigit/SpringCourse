package br.com.udemycourse.demo;


import br.com.udemycourse.demo.Exceptions.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculadora")
public class MathController {

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo){
        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    @GetMapping("/subtraction/{numberOne}/{numberTwo}")
    public Double subtraction(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo){
        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }
    @GetMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo){
        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }
    @GetMapping("/division/{numberOne}/{numberTwo}")
    public Double division(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo){
        return convertToDouble(numberOne) / convertToDouble(numberTwo);
    }

    @GetMapping("/mean/{numberOne}/{numberTwo}")
    public Double mean(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo){
        return (convertToDouble(numberOne) + convertToDouble(numberTwo)) / 2;
    }

    @GetMapping("/sqrt/{number}")
    public Double sqrt(@PathVariable(value = "number") String number){
        return Math.sqrt(convertToDouble(number));
    }
    private Double convertToDouble(String strNumber){
        if(strNumber == null) throw new NullPointerException();
        // BR 10,25 US 10.15
        String number = strNumber.replaceAll(",", ".");
        if(isNumeric(number)) return Double.parseDouble(number);
        else{
            throw new UnsupportedMathOperationException("Please set a numeric value");
        }
    }

    private boolean isNumeric(String number) {
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

}
