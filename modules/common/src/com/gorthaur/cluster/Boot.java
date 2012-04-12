package com.gorthaur.cluster;

import java.io.File;

import javax.inject.Inject;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.gorthaur.cluster.console.server.ApplicationContextListener;
import com.gorthaur.cluster.web.WebContainer;

public class Boot {

	@Inject
	ClusterStateManager channelManager;
	
	@Inject CommandLine commandLine;
	
	@Inject
	Injector injector;
	
	void start() throws Exception {
		
		ApplicationContextListener.setInjector(injector);
		
		if(commandLine.hasOption("console")) {
			int consolePort = Integer.valueOf(commandLine.getOptionValue("console"));
			System.out.println("Console On Port " + consolePort);
			if(new File("lib/console.war").exists()) {
				new WebContainer(consolePort, new File("lib/console.war"));
			} else if(new File("modules/console/war").exists()) {
				new WebContainer(consolePort, new File("modules/console/war"));
			} else {
				System.out.println("Console war not found!");
			}
		} else {
			System.out.println("Launch Slave");
		}
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		
		Options options = new Options();
		options.addOption(OptionBuilder.withArgName("port")
				.hasArg()
				.withDescription("start a management console")
				.create("console"));
		
		CommandLineParser parser = new GnuParser();
		try {
			final CommandLine line = parser.parse( options, args );
			
		    
			Guice.createInjector(new AbstractModule(){

				@Override
				protected void configure() {
					bind(CommandLine.class).toInstance(line);
				}
				
			}).getInstance(Boot.class).start();	
			
		} catch (Exception ex) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "<jarname>", options );
		}
	
	}
	
}
