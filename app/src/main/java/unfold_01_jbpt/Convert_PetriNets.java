package unfold_01_jbpt;

import org.jbpt.petri.NetSystem;
import org.jbpt.petri.Node;

import hub.top.petrinet.Arc;
import hub.top.petrinet.PetriNet;
import hub.top.petrinet.Place;
import hub.top.petrinet.Transition;

public class Convert_PetriNets {
	
	public static PetriNet jbpt_to_hub(org.jbpt.petri.NetSystem net) {
		System.out.println("============================================= > jbpt_to_hub");
		PetriNet pnet = new PetriNet();
		
		for(org.jbpt.petri.Place p : net.getPlaces()) {
			pnet.addPlace(p.getName());
			System.out.println("Place name: " + "[" + p.getName() + "]");
		}
		
		for(org.jbpt.petri.Transition t: net.getTransitions()) {
			pnet.addTransition( t.getLabel() );
		}
		
		for(org.jbpt.petri.Flow f: net.getFlow()) {
			System.out.println( "S: " + f.getSource().getName() + ":" + f.getSource().getLabel());
			System.out.println( "T: " + f.getTarget().getName() + ":" + f.getSource().getLabel());
			
			pnet.addArc( f.getSource().getName(),  f.getTarget().getName());
		}
	
		for(org.jbpt.petri.Place p: net.getSourcePlaces())
			pnet.findPlace(p.getName()).setTokens(1);
		
		System.out.println("Converted PNET");
		System.out.println(pnet);
		return pnet;
	}
	
	public static NetSystem hub_to_jbpt(PetriNet pnet) {
		System.out.println("============================================= > hub_to_jbpt");
		NetSystem net = new NetSystem();
		
		for(Place p: pnet.getPlaces()) {
			net.addPlace(new org.jbpt.petri.Place(p.getName(), p.getName()));
			System.out.println("Place name: " + "[" + p.getName() + "]");
		}
			
		for(Transition t: pnet.getTransitions()) {
			net.addTransition(new org.jbpt.petri.Transition(t.getName(), t.getName()));
			System.out.println("Transition name: " + "[" + t.getName() + "]");
		}
		
		for(Arc arc: pnet.getArcs()) {
			Node source = find_node_by_name( arc.getSource().getName(), net);
			Node target = find_node_by_name( arc.getTarget().getName(), net);
			net.addFlow(source, target);			
		}
		
		for(Place p: pnet.getPlaces()) {
			if(p.getTokens() > 0) {	
				org.jbpt.petri.Place np = ((org.jbpt.petri.Place)find_node_by_name(p.getName(), net));
				net.putTokens(np,p.getTokens());
			}				
		}
		
		return net;
	}
	
	private static Node find_node_by_name(String name, NetSystem net) {		
		for(org.jbpt.petri.Place p: net.getPlaces())
			if(p.getName().equals( name ))
				return p;

		for(org.jbpt.petri.Transition t: net.getTransitions())
			if(t.getLabel().equals( name ))
				return t;

		System.out.println("Return null: [" + name + "]");
		return null;
	}
	
	private static hub.top.petrinet.Node find_node_by_name(String name, PetriNet pnet) {		
		for(Place p: pnet.getPlaces())
			if(p.getName().equals( name ))
				return p;

		for(Transition t: pnet.getTransitions())
			if(t.getName().equals(name))
				return t;

		System.out.println("Return null: [" + name + "]");
		return null;
	}
}
