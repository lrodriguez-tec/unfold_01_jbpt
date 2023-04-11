package utilities;

import hub.top.petrinet.PetriNet;
import hub.top.petrinet.Place;
import hub.top.petrinet.Transition;
import hub.top.petrinet.Node;

import java.io.FileOutputStream;

//import ee.ut.nets.unfolding.BPstructBP.MODE;

import java.util.*;
import java.util.stream.Collectors;


public class PNMLUtil{

	//Process Places
	static String processPlaces(PetriNet net, Map<String, String> ids) {
		return net.getPlaces().stream().map(p ->
				  """
						<place id="%s">
						  <name>
							<text>%s</text>
						  </name>
				  """.formatted(ids.get(p.getUniqueIdentifier()), p.getName()) + (p.getTokens() > 0 ?
				  """
						  <initialMarking>
							<text>1</text>
						  </initialMarking>
				  """ : "") + "</place>"
		).collect(Collectors.joining("\n"));
	}

	//Process Transitions
	static String processTransitions(PetriNet net, Map<String, String> ids) {
		return net.getTransitions().stream().map(t ->
				"""
					  <transition id="%s">
						<name>
						  <text>%s</text>
						</name>
				""".formatted(ids.get(t.getUniqueIdentifier()), t.getName()) + (t.tau ?
								"""
					                <toolspecific tool="ProM" version="6.4" activity="$invisible$"/>
								""" : ""
										) + "     </transition>"
		).collect(Collectors.joining("\n"));
	}

	//Process Arcs
	static String processArcs(PetriNet net, Map<String, String> ids) {
		return net.getArcs().stream().map(a ->
				"      <arc id=\"%s\" source=\"%s\" target=\"%s\" />".formatted(
						ids.get(a.getSource().getUniqueIdentifier()) + "_" + ids.get(a.getTarget().getUniqueIdentifier()),
						ids.get(a.getSource().getUniqueIdentifier()), ids.get(a.getTarget().getUniqueIdentifier())
				)
		).collect(Collectors.joining("\n"));
	}
	
	static String processFinalMarkings(List<Place> places, Map<String, String> ids) {
		return places.stream().map(p ->
		"""
			<marking>
					<place idref="%s">
						<text>1</text>
					</place>
				</marking>
		""".formatted(ids.get(p.getUniqueIdentifier()))).collect(Collectors.joining("\n"));		
	}
	
	static String finalMarkings(PetriNet net, Map<String, String> ids, List<Place> final_places) {
		if(final_places.size() == 0)
			return "";
		
		var a =
				"""
				<finalmarkings>
					%s
					</finalmarkings>
				""";			
		return a.formatted(processFinalMarkings(final_places, ids));		
	}
	
	static public String create_pnml(PetriNet pn){
		List<Place> empty_final_places = new ArrayList<>();
		return create_pnml(pn, empty_final_places);
	}

	//map: places, transitions, arcs
	static public String create_pnml(PetriNet pn,  List<Place> final_places){
		
		Map<String, String> map = new HashMap<>();

		for (Place p: pn.getPlaces())
			map.put(p.getUniqueIdentifier(), "p" + map.size());
		for (Transition t: pn.getTransitions())
			map.put(t.getUniqueIdentifier(), "t" + (map.size() - pn.getPlaces().size()));
		
		var a =
			"""
			<?xml version='1.0' encoding='UTF-8'?>
			<pnml>
			  <net id="new_petri_net" type="http://www.pnml.org/version-2009/grammar/pnmlcoremodel">
				<name>
				  <text>new_petri_net</text>
				</name>
				<page id="n0">
			%s
			%s
			%s
				</page>
				%s
			  </net>
			</pnml>
			""";
		
		return a.formatted(processPlaces(pn, map), processTransitions(pn, map), processArcs(pn, map), finalMarkings(pn, map, final_places));
	}
	
	public static void create_pnml_to_file(PetriNet pn,  List<Place> final_places, String file_name) {
		String pnml_string = create_pnml(pn, final_places);
		System.out.println("======================================== XML");
		System.out.println(pnml_string);
		
		try {
			FileOutputStream fo = new FileOutputStream(file_name);
			fo.write( pnml_string.getBytes() );
			fo.close();			
		} catch (Exception e) {
			System.err.println("Error PNMLUtil: " + e.getCause());
			e.printStackTrace();
		}		
		System.out.println("======================================== XML");
	}
	
	public static void create_pnml_to_file(PetriNet pn, String file_name) {
		System.out.println("======================================== PNML");		
		List<Place> empty_final_places = new ArrayList<>();
		create_pnml_to_file(pn, empty_final_places, file_name);		
		System.out.println("======================================== PNML");
	}
}
