package utilities;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import de.lukaskoerfer.simplepnml.Collectable;
import de.lukaskoerfer.simplepnml.Document;
import de.lukaskoerfer.simplepnml.Label;
import de.lukaskoerfer.simplepnml.Net;
import de.lukaskoerfer.simplepnml.Page;
import de.lukaskoerfer.simplepnml.ToolSpecific;
import hub.top.petrinet.Arc;
import hub.top.petrinet.Node;
import hub.top.petrinet.PetriNet;
import hub.top.petrinet.Place;
import hub.top.petrinet.Transition;

public class SimplePNMLUtil {

	static void add_places(Page page, PetriNet pn) {
		for (Place p: pn.getPlaces()){
			System.out.println("Place-> Name: [" + p.getName() + "], Identifier: [" + p.getUniqueIdentifier() + "]");			
			page.getPlaces().add(
					de.lukaskoerfer.simplepnml.Place.builder()
					.id( p.getUniqueIdentifier() )
					.name(new Label(p.getName()))
					.initialMarking(new Label( p.getTokens() + ""))
					.build()
			);
		}		
	}
	
	static void add_transitions(Page page, PetriNet pn) {
		for (Transition t: pn.getTransitions()) {
			System.out.println("Transition-> Name:[" + t.getName() + "], Identifier: [" + t.getUniqueIdentifier() + "]");
			
//			ToolSpecific ts = new ToolSpecific("Prom", "6.4");
			
			page.getTransitions().add(
				de.lukaskoerfer.simplepnml.Transition.builder()
				.id(t.getUniqueIdentifier())
				.name(new Label(t.getName()))
//				.toolSpecific(ts)
				.build()
			);			
		}
	}
	
	static void add_arcs(Page page, PetriNet pn) {
		for (Arc a: pn.getArcs()) {
			System.out.println("Arc-> source: [" + a.getSource().getUniqueIdentifier() + "], Target: [" + a.getTarget().getUniqueIdentifier() + "]");
			page.getArcs().add(
				de.lukaskoerfer.simplepnml.Arc.builder()
				.source(a.getSource().getUniqueIdentifier())
				.target(a.getTarget().getUniqueIdentifier())
				.build()
			);
		}
	}
	
	
	public static String create_pnml(PetriNet pn) {
		Page page = new Page();		
		
		add_places(page, pn);
		add_transitions(page, pn);
		add_arcs(page, pn);
		
		Net pnet = new Net("Petri_Net");
		
		List<Page> pages = new LinkedList<Page>();
		pages.add(page);
		pnet.setPages(pages);

		List<Net> nets = new ArrayList<>();
		nets.add(pnet);

		Document doc = new Document();
		doc.setNets(nets);
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			var context = JAXBContext.newInstance(Document.class);
	        var marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);	        
	        
			marshaller.marshal(doc, stream);			
		} catch (Exception e) {
			System.err.println("Error SimplePNMLUtil: " + e.getCause());
			e.printStackTrace();
		}	
				
		return stream.toString();
	}	
	
	public static void create_pnml_to_file(PetriNet pn, String file_name) {
		String pnml_string = create_pnml(pn);
		System.out.println("======================================== XML");
		System.out.println(pnml_string);
		
		try {
			FileOutputStream fo = new FileOutputStream(file_name);
			fo.write( pnml_string.getBytes() );
			fo.close();			
		} catch (Exception e) {
			System.err.println("Error SimplePNMLUtil: " + e.getCause());
			e.printStackTrace();
		}		
		System.out.println("======================================== XML");
	}
}
