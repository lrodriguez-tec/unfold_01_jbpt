package unfold_01_jbpt;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.jbpt.petri.NetSystem;
import org.jbpt.petri.Transition;
import org.jbpt.petri.io.PNMLSerializer;
import org.jbpt.utils.IOUtils;

import hub.top.petrinet.PetriNet;

public class App {
	
	public static void print_help(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("unfold:", options);		
	}

    public static void main(String[] args) throws Exception{		
    	Options cli_options = new Options();
    	cli_options.addOption("f", "filename", true, "Pnml file name to be unfold");
				
    	CommandLineParser parser = new DefaultParser();
    	CommandLine cmd = parser.parse(cli_options, args);
    	String filename = "";
    	
    	if(cmd.hasOption("f")) {
    		filename = cmd.getOptionValue("f");
    		System.out.println("Input pnml filename: " + filename);
    	}
    	else {
    		print_help(cli_options);
    		System.exit(0);
    	}
    	    	
    	NetSystem net = new PNMLSerializer().parse(filename);
    	IOUtils.toFile(filename + ".dot", net.toDOT());    	    	
    	
    	PetriNet pn = Convert_PetriNets.jbpt_to_hub(net);
    	
    	NetSystem net2 = Convert_PetriNets.hub_to_jbpt(pn);
    	
    	IOUtils.saveDocumentToFile(PNMLSerializer.serialize(net2), "SalidaExito.pnml");
    	
    	System.out.println("Petri Net: [" + net + "]");
    }
}
