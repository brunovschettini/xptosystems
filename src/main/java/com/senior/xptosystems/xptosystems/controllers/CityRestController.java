package com.senior.xptosystems.xptosystems.controllers;

import com.senior.xptosystems.xptosystems.utils.StringHelper;
import antlr.StringUtils;
import com.senior.xptosystems.xptosystems.model.City;
import com.senior.xptosystems.xptosystems.model.Mesoregion;
import com.senior.xptosystems.xptosystems.model.Microregion;
import com.senior.xptosystems.xptosystems.model.Uf;
import com.senior.xptosystems.xptosystems.repositories.CityRepository;
import com.senior.xptosystems.xptosystems.repositories.MesoregionRepository;
import com.senior.xptosystems.xptosystems.repositories.MicroregionRepository;
import com.senior.xptosystems.xptosystems.repositories.UfRepository;
import com.senior.xptosystems.xptosystems.services.ICityComponent;
import com.senior.xptosystems.xptosystems.utils.Result;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/city")
public class CityRestController {

    @Autowired
    ICityComponent cityComponent;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    UfRepository ufRepository;

    @Autowired
    MicroregionRepository microregionRepository;

    @Autowired
    MesoregionRepository mesoregionRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ResponseEntity<?> receiveData(MultipartFile csv) throws IOException {
        if (csv.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<City> cities = new ArrayList();
        cities = cityComponent.uploadCSV(csv, true);
        if (!cities.isEmpty()) {
            cityComponent.storeAll(cities);
            return new ResponseEntity<>(cities, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/capitals")
    public ResponseEntity<?> capitals() throws IOException {
        List<City> list = cityRepository.findByCapitalOrderByNameAsc();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/uf/min_max")
    public ResponseEntity<?> uf_min_max() throws IOException {
        List<Object[]> rows = cityRepository.findMinMaxCitiesByUf();
        JSONArray array = new JSONArray();
        for (Object[] row : rows) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("uf", row[0]);
            jSONObject.put("amount", row[1]);
            array.put(jSONObject);
        }
        Result result = new Result();
        result.setResult(array);
        if (array.length() == 0) {
            return new ResponseEntity<>(array, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/total")
    public ResponseEntity<?> total() {
        Result result = new Result();
        Integer total = cityRepository.total();
        result.setResult(total);
        if (total == 0) {
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/total/{column}")
    public ResponseEntity<?> total(@PathVariable("column") String column) {
        if (column == null || column.isEmpty()) {
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }
        String queryString = "";
        switch (column) {
            case "ibge_id":
                queryString = "SELECT count(*) FROM city C GROUP BY C.ibge_id ";
                break;
            case "uf":
                queryString = "SELECT count(*) FROM city C GROUP BY C.uf_id ";
                break;
            case "name":
                queryString = "SELECT count(*) FROM city C GROUP BY C.name ";
                break;
            case "no_accents":
                queryString = "SELECT count(*) FROM city C GROUP BY C.no_accents ";
                break;
            case "alternative_names":
                queryString = "SELECT count(*) FROM city C GROUP BY C.alternative_names ";
                break;
            case "micro_region":
                queryString = "SELECT count(*) FROM city C GROUP BY C.microregions_id ";
                break;
            case "meso_region":
                queryString = "SELECT count(*) FROM city C GROUP BY C.mesoregions_id ";
                break;
            case "capital":
                queryString = "SELECT count(*) FROM city C WHERE C.capital = 1 ";
                break;
            default:
                break;
        }
        try {
            Object result = cityRepository.total(queryString);
        } catch (Exception e) {

        }

        Result result = new Result();
        Integer total = cityRepository.total();
        result.setResult(total);
        if (total == 0) {
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/uf/{uf}")
    public ResponseEntity<?> findByUf(@PathVariable("uf") String uf_name) throws IOException {
        if (uf_name == null || uf_name.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Uf uf = ufRepository.findByNameIgnoreCase(uf_name);
        JSONArray array = new JSONArray();
        if (uf != null) {
            Integer count = cityRepository.findCountByUf(uf.getId());
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("uf", uf.getName());
            jSONObject.put("count", count);
            array.put(jSONObject);
            Result result = new Result();
            result.setResult(array);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ibge_id/{ibge_id}")
    public ResponseEntity<?> findByIbgeId(@PathVariable("ibge_id") Long ibge_id) {
        if (ibge_id == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        City city = cityRepository.findByIbge_id(ibge_id);
        JSONArray array = new JSONArray();
        if (city != null) {
            return new ResponseEntity<>(city, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/city/store", method = RequestMethod.POST)
    public ResponseEntity<?> store(@RequestBody City city) {
        Result result = new Result();
        if (city.getId() != null) {
            result.setStatus_code(0);
            result.setStatus("exit city! use PUT to update!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (city.getName().isEmpty()) {
            result.setStatus_code(0);
            result.setStatus("empty name!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (city.getUfName().isEmpty()) {
            result.setStatus_code(0);
            result.setStatus("empty uf!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        Uf uf = ufRepository.findByNameIgnoreCase(city.getUfName());
        if (uf == null) {
            uf = new Uf();
            uf.setName(city.getUfName());
            ufRepository.save(uf);
        }
        city.setUf(uf);
        if (city.getMesoregionName().isEmpty()) {
            result.setStatus_code(0);
            result.setStatus("empty mesoregion aux!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        Mesoregion mesoregion = mesoregionRepository.findByNameIgnoreCase(city.getMesoregionName());
        if (mesoregion == null) {
            mesoregion = new Mesoregion();
            mesoregion.setName(city.getUfName());
            mesoregionRepository.save(mesoregion);
        }
        city.setMesoregions(mesoregion);
        if (city.getMicroregionName().isEmpty()) {
            result.setStatus_code(0);
            result.setStatus("empty microregion aux!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        Microregion microregion = microregionRepository.findByNameIgnoreCase(city.getMicroregionName());
        if (microregion == null) {
            microregion = new Microregion();
            microregion.setName(city.getUfName());
            microregionRepository.save(microregion);
        }
        city.setNoAccents(StringHelper.unaccent(city.getName()));
        city.setMicroregions(microregion);
        try {
            cityRepository.save(city);
        } catch (Exception e) {
            result.setStatus_code(0);
            result.setStatus("city e->" + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.setStatus("success: city nº " + city.getId() + " registered");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/city/store", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody City city) {
        Result result = new Result();
        if (city.getId() == null) {
            result.setStatus_code(0);
            result.setStatus("new register! use POST to store/save!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (city.getName().isEmpty()) {
            result.setStatus_code(0);
            result.setStatus("empty name!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (city.getUfName().isEmpty()) {
            result.setStatus_code(0);
            result.setStatus("empty uf!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        Uf uf = ufRepository.findByNameIgnoreCase(city.getUfName());
        if (uf == null) {
            uf = new Uf();
            uf.setName(city.getUfName());
            ufRepository.save(uf);
        }
        city.setUf(uf);
        if (city.getMesoregionName().isEmpty()) {
            result.setStatus_code(0);
            result.setStatus("empty mesoregion aux!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        Mesoregion mesoregion = mesoregionRepository.findByNameIgnoreCase(city.getMesoregionName());
        if (mesoregion == null) {
            mesoregion = new Mesoregion();
            mesoregion.setName(city.getUfName());
            mesoregionRepository.save(mesoregion);
        }
        city.setMesoregions(mesoregion);
        if (city.getMicroregionName().isEmpty()) {
            result.setStatus_code(0);
            result.setStatus("empty microregion aux!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        Microregion microregion = microregionRepository.findByNameIgnoreCase(city.getMicroregionName());
        if (microregion == null) {
            microregion = new Microregion();
            microregion.setName(city.getUfName());
            microregionRepository.save(microregion);
        }
        city.setNoAccents(StringHelper.unaccent(city.getName()));
        city.setMicroregions(microregion);
        try {
            cityRepository.save(city);
        } catch (Exception e) {
            result.setStatus_code(0);
            result.setStatus("city e->" + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.setStatus("success: city nº " + city.getId() + " registered");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/city/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Result result = new Result();
        if (id == null) {
            result.setStatus_code(0);
            result.setStatus("empty order id!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        City city = cityRepository.getOne(id);
        if (city == null) {
            result.setStatus_code(0);
            result.setStatus("empty city!");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        try {
            cityRepository.delete(city);
            // em.flush();
        } catch (Exception e) {
            result.setStatus_code(0);
            result.setStatus("order e->" + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.setStatus("success: order nº " + city.getId() + " removed");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    
//    @Context
//    HttpHeaders headers;
//
//    @POST
//    @Path("/import")
//    @Produces({MediaType.APPLICATION_JSON})
//    public synchronized Response importCities(@FormParam("list_cities") String list_cities) {
//        NotifyResponse notifyResponse = new NotifyResponse();
//        Gson gson = new Gson();
//        List<Cities> listCities = new ArrayList();
//        listCities = gson.fromJson(list_cities, new TypeToken<List<Cities>>() {
//        }.getType());
//        Dao dao = new Dao();
//        for (int i = 0; i < listCities.size(); i++) {
//            dao.save(listCities.get(i), true);
//        }
//        notifyResponse.setObject("OK");
//        return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//    }
//
//    /**
//     * 2 Retornar somente as cidades que são capitais ordenadas por nome
//     *
//     * @return
//     */
//    @GET
//    @Path("/capitals")
//    @Produces({MediaType.APPLICATION_JSON})
//    public synchronized Response capitals() {
//        return Response.status(200).entity(new Gson().toJson(new CitiesDao().capitals())).build();
//    }
//
//    /**
//     * 3 Retornar o nome do estado com a maior e menor quantidade de cidades e a
//     * quantidade de cidades
//     *
//     * @return
//     */
//    @GET
//    @Path("/min_max_cities_by_state")
//    @Produces({MediaType.APPLICATION_JSON})
//    public synchronized Response min_max_cities_by_state() {
//        List<Object[]> rows = new CitiesDao().min_max_cities_by_state();
//        JSONArray array = new JSONArray();
//        for (Object[] row : rows) {
//            JSONObject jSONObject = new JSONObject();
//            jSONObject.put("uf", row[0]);
//            jSONObject.put("amount", row[1]);
//            array.put(jSONObject);
//        }
//        return Response.status(200).entity(array.toString()).build();
//    }
//
//    /**
//     * 4 Retornar a quantidade de cidades por estao
//     *
//     * @param uf
//     * @return
//     */
//    @GET
//    @Path("/count_by_state/{uf}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public synchronized Response count_by_state(@PathParam("uf") String uf) {
//        Integer count = new CitiesDao().count_by_state(uf);
//        JSONObject jSONObject = new JSONObject();
//        jSONObject.put("uf", uf);
//        jSONObject.put("amount", count);
//        return Response.status(200).entity(jSONObject.toString()).build();
//    }
//
//    /**
//     * 5 Obter os dados da cidade informando o id do IBGE
//     *
//     * @param ibge_id
//     * @return
//     */
//    @GET
//    @Path("/ibge_id/{ibge_id}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public synchronized Response ibge_id(@PathParam("ibge_id") String ibge_id) {
//        Cities cities = new CitiesDao().find_by_ibge_id(ibge_id);
//        List<Cities> list = new ArrayList();
//        if(cities != null) {
//            list.add(cities);            
//        }
//        return Response.status(200).entity(new Gson().toJson(list)).build();
//    }
//
//    /**
//     * 6 Retornar o nome das cidades baseado em um estado selecionado
//     *
//     * @param uf
//     * @return
//     */
//    @GET
//    @Path("/uf/{uf}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public synchronized Response find_uf(@PathParam("uf") String uf) {
//        List<Cities> list = new CitiesDao().find_by_uf(uf);
//        return Response.status(200).entity(new Gson().toJson(list)).build();
//    }
//
//    /**
//     * 7 Permitir adicionar uma nova Cidade
//     *
//     *
//     * @param ibge_id
//     * @param uf
//     * @param name
//     * @param capital
//     * @param lon
//     * @param lat
//     * @param no_accents
//     * @param alternative_names
//     * @param micro_region
//     * @param meso_region
//     * @return
//     */
//    @POST
//    @Path("insert")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public synchronized Response insert(
//            @FormParam("ibge_id") String ibge_id,
//            @FormParam("uf") String uf,
//            @FormParam("name") String name,
//            @FormParam("capital") String capital,
//            @FormParam("lon") String lon,
//            @FormParam("lat") String lat,
//            @FormParam("no_accents") String no_accents,
//            @FormParam("alternative_names") String alternative_names,
//            @FormParam("micro_region") String micro_region,
//            @FormParam("meso_region") String meso_region
//    ) {
//        NotifyResponse notifyResponse = new NotifyResponse();
//        Gson gson = new Gson();
//
//        if (ibge_id == null || ibge_id.isEmpty()) {
//            notifyResponse.setObject("Informar o Id do Ibge!");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        if (name == null || name.isEmpty()) {
//            notifyResponse.setObject("Informar o nome da ciade!");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        if (uf == null || uf.isEmpty()) {
//            notifyResponse.setObject("Informar a UF / Estado da ciade!");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        if (lat == null || lat.isEmpty()) {
//            notifyResponse.setObject("Informar as cordenadas de latitude!");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        if (lon == null || lon.isEmpty()) {
//            notifyResponse.setObject("Informar as cordenadas de longitude!");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        if (capital == null || capital.isEmpty()) {
//            notifyResponse.setObject("Informar se a cidade é uma capital!");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        Cities city = new Cities(null, Long.parseLong(ibge_id), uf, name, Boolean.parseBoolean(capital), Double.parseDouble(lon), Double.parseDouble(lat), no_accents, alternative_names, micro_region, meso_region, new Date());
//        String result = "";
//        if (!new Dao().save(city, true)) {
//            notifyResponse.setObject("Error");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        notifyResponse.setObject("Success");
//        return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//    }
//
//    @POST
//    @Path("/store")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public Response store(@FormParam("cities") String citiesJson) {
//        Gson gson = new Gson();
//        NotifyResponse notifyResponse = new NotifyResponse();
//        Cities c = new Gson().fromJson(citiesJson, Cities.class);
//
//        if (c.getIbge_id() == null || c.getIbge_id().equals(new Long(0))) {
//            notifyResponse.setObject("Informar o Id do Ibge!");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        if (c.getName() == null || c.getName().isEmpty()) {
//            notifyResponse.setObject("Informar o nome da ciade!");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        if (c.getUf() == null || c.getUf().isEmpty()) {
//            notifyResponse.setObject("Informar a UF / Estado da ciade!");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        if (c.getLat() == null) {
//            notifyResponse.setObject("Informar as cordenadas de latitude!");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        if (c.getLon() == null) {
//            notifyResponse.setObject("Informar as cordenadas de longitude!");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        if (c.getCapital() == null) {
//            notifyResponse.setObject("Informar se a cidade é uma capital!");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        String result = "Cities cities: " + c;
//        CitiesDao citiesDao = new CitiesDao();
//        if (citiesDao.exists(c) != null) {
//            notifyResponse.setObject("Cidade para esse UF já cadastrada!");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        if (citiesDao.findByIBGE(c.getIbge_id()) != null) {
//            notifyResponse.setObject("Cidade já cadastrada com esse código do IBGE!");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        Dao dao = new Dao();
//        if (!dao.save(c, true)) {
//            notifyResponse.setObject("Erro ao inserir registro!");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        return Response.status(200).entity(gson.toJson(c)).build();
//
//    }
//
//    /**
//     * 8 Permitir deletar uma cidade
//     *
//     * @return
//     */
//    @DELETE
//    @Path("/delete")
//    @Produces({MediaType.APPLICATION_JSON})
//    public synchronized Response delete() {
//        NotifyResponse notifyResponse = new NotifyResponse();
//        Gson gson = new Gson();
//        String ibge_id = headers.getRequestHeader("ibge_id").get(0);
//        if (ibge_id == null || ibge_id.isEmpty()) {
//            notifyResponse.setObject("Empty / null ibge id");
//            return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//        }
//        Cities c = new CitiesDao().findByIBGE(Long.parseLong(ibge_id));
//        if (c != null) {
//            Dao dao = new Dao();
//            if (!dao.delete(c, true)) {
//                notifyResponse.setObject("Erro ao excluir registro!");
//                return Response.status(200).entity(gson.toJson(notifyResponse)).build();
//            }
//        }
//        return Response.status(200).entity(gson.toJson(c)).build();
//    }
//
//    /**
//     * 9 Permitir selecionar uma coluna (do CSV) e através dela entrar com uma
//     * string para filtrar. retornar assim todos os objetos que contenham tal
//     * string
//     *
//     * @param column
//     * @param query
//     * @return
//     */
//    @GET
//    @Path("/search/{column}/{query}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public synchronized Response find(@PathParam("column") String column, @PathParam("query") String query) {
//        List<Cities> list = new CitiesDao().find(column, query);
//        return Response.status(200).entity(new Gson().toJson(list)).build();
//    }
//
//    /**
//     * 10 Retornar a quantidade de registro baseado em uma coluna. Não deve
//     * contar itens iguais
//     *
//     * @param column
//     * @return
//     */
//    @GET
//    @Path("/total_column/{column}")
//    @Produces({MediaType.APPLICATION_JSON})
//    public synchronized Response total_column(@PathParam("column") String column) {
//        Integer total = new CitiesDao().total(column);
//        JSONObject jSONObject = new JSONObject();
//        jSONObject.put("total", total);
//        return Response.status(200).entity(jSONObject.toString()).build();
//    }
//
//    @GET
//    @Path("/total")
//    @Produces({MediaType.APPLICATION_JSON})
//    public synchronized Response total() {
//        Integer total = new CitiesDao().total();
//        JSONObject jSONObject = new JSONObject();
//        jSONObject.put("total", total);
//        return Response.status(200).entity(jSONObject.toString()).build();
//    }
}
