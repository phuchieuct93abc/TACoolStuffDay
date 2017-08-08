package ch.axonivy.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import ch.ivyteam.ivy.business.data.store.BusinessDataRepository;
import ch.ivyteam.ivy.environment.Ivy;

import com.google.gson.Gson;
public class PersistenceUtil {

	public static final int MAX_SIZE = 10000;

	public static <T> List<T> retrieveFromPersistence(Class<T> klz) {
		return BusinessDataRepository.get().search(klz).limit(MAX_SIZE).execute().getAll();
	}
	
	public static String toJSON(Dossier soba){
		Gson gson = new Gson();
        String json = gson.toJson(soba);
        return json;
	}
	public static List<DossierResult> retrieveDossierFromPersistenceAsString(){
		List<Dossier> results = retrieveFromPersistence(Dossier.class);
		List<DossierResult> values = new ArrayList<DossierResult>();
		if(CollectionUtils.isNotEmpty(results)){
			values = results.stream()
					.map(item -> {
				Gson gson = new Gson();
                String json = gson.toJson(item);
                System.out.println(json);
                DossierResult result = new DossierResult();
                result.setDetail(json);
                return result;
			}).collect(Collectors.toList());
		}
		return values;
	}
	
	public static void cleanupDB(){
		List<Dossier> results = retrieveFromPersistence(Dossier.class);
		deleteObjects(results);
	}
	
	
	
	public static <T> void deleteObjects(List<T> objs) {
		objs.stream().forEach( value -> {
			BusinessDataRepository.get().delete(value);
		});
	}
	
	public static <T> void saveObjects(List<T> objs) {
		objs.forEach(value -> {
			try {
				BusinessDataRepository.get().save(value);
			} catch (Exception e) {
				Ivy.log().error("Cannot persist dummy data", e);
			}
		});
	}
	
	
}
