package br.puc.mestrado.rebeca.utils;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import br.puc.mestrado.rebeca.config.Config;

public class XMLUtils {

	public static Config readConfigFile(String filepath) {
		return readConfigFile(new File(filepath));
	}

	public static Config saveConfigFile(File file, Config c) {
		Config config = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.marshal(c, file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return config;
	}

	public static Config readConfigFile(File file) {
		Config config = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			config = (Config) jaxbUnmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return config;

	}

}
