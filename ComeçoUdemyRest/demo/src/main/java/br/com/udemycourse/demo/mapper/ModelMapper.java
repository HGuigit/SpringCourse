package br.com.udemycourse.demo.mapper;

import br.com.udemycourse.demo.Models.Person;
import br.com.udemycourse.demo.data.vo.v1.PersonVO;
import org.dozer.DozerBeanMapper;
import org.modelmapper.TypeMap;

import java.util.ArrayList;
import java.util.List;

public class ModelMapper {

    public static DozerBeanMapper mapper = new DozerBeanMapper();

    public  static  <O, D> D parseObject(O origin, Class<D> destination){

        return  mapper.map(origin, destination);
    }

    public  static  <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination){
        List<D> destinationObjects = new ArrayList<D>();
        origin.forEach((O originObject) -> destinationObjects.add(parseObject(originObject, destination)));
        return  destinationObjects;
    }

}
