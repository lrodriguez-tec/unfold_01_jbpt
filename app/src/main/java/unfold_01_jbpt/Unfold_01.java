package unfold_01_jbpt;

import hub.top.petrinet.PetriNet;
import hub.top.petrinet.Place;
import hub.top.petrinet.Transition;
import hub.top.petrinet.Node;

import org.jbpt.petri.NetSystem;
import org.jbpt.utils.IOUtils;

import ee.ut.nets.unfolding.BPstructBP.MODE;
import ee.ut.nets.unfolding.Unfolder_PetriNet;

import java.util.*;

public class Unfold_01 {
	
	public static NetSystem unfold(NetSystem net) throws Exception{

		NetSystem unfols_ns = null;
		return unfols_ns;
//		
//		System.out.println( "==================================================>> CantitatePaperTest");
//		PetriNet net = new PetriNet();
//		Place p0 = net.addPlace("p0");
//		Place p1 = net.addPlace("p1");
//		Place p2 = net.addPlace("p2");
//		Place p3 = net.addPlace("p3");
//
//		Transition t0 = net.addTransition("A");
//		Transition t1 = net.addTransition("B");
//		Transition t2 = net.addTransition("C");
//		Transition t3 = net.addTransition("D");
//
//		net.addArc(p0, t0);
//		net.addArc(t0, p1);
//		net.addArc(p1, t1);
//		net.addArc(t1, p2);
//
//		net.addArc(p2, t2);
//		net.addArc(t2, p1);
//
//		net.addArc(p2, t3);
//		net.addArc(t3, p3);
//
//		p0.setTokens(1);
//
//		Unfolder_PetriNet unfolder = new Unfolder_PetriNet(net, MODE.ONEUNFOLDING);
//		unfolder.computeUnfolding();
//		IOUtils.toFile("net2.dot", net.toDot());
//		IOUtils.toFile("bp2.dot", unfolder.getUnfoldingAsDot());
//
//		PetriNet unf = unfolder.getUnfoldingAsPetriNet();
//
//
//		Set<Node> toRemove = new HashSet<>(unf.getPlaces());
//		toRemove.addAll(unf.getTransitions());
//
//		ArrayList<Node> finals = new ArrayList<>();
//		for (Place p: unf.getPlaces())
//			if (p.getPostSet().isEmpty() && !p.getName().startsWith("CUT"))
//				finals.add(p);
//
//		Set<Node> visited = new HashSet<>();
//		Stack<Node> open = new Stack<>();
//		finals.forEach(n -> open.push(n));
//		while (!open.isEmpty()) {
//			Node curr = open.pop();
//			toRemove.remove(curr);
//			visited.add(curr);
//			for (Node pred: curr.getPreSet())
//				if (!visited.contains(pred) && !open.contains(pred))
//					open.push(pred);
//		}
//
//		System.out.println("To remove: " + toRemove);
//
//		for (Node n: toRemove)
//			if (n instanceof Place)
//				unf.removePlace((Place) n);
//			else
//				unf.removeTransition((Transition) n);
//		IOUtils.toFile("pruned2.dot", unf.toDot());
//
////		PNMLUtil.create_pnml_to_file(net, "pnml_files/candidate_pnml_net.pnml");
////		PNMLUtil.create_pnml_to_file(unf, "pnml_files/candidate_pnml_unf.pnml");
//
//		System.out.println( "==================================================>> END CantitatePaperTest");
	}
}