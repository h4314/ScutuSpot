package network;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

/**
 * La classe Datagram permet d'écrire ou lire un paquet traité par un capteur.
 * @author H4314
 */
public class Datagram
{
	/** Litéral séparateur */
	public static final String SEPARATOR = ";";
	
	/** Contenu du datagramme */
	protected String datagram;
	/** Identifiant du sunspot émetteur du message */
	protected String sunspotId;
	/** Attributs contenus dans le datagramme */
	protected Map<String, String> attributes;
	
	/**
	 * Le constructeur admet un premier fragment comme paramètre si souhaité.
	 * @param fragment
	 */
	public Datagram(String fragment) {
		datagram = fragment;
		sunspotId ="";
		attributes = new HashMap<String, String>();
	}
	
	/**
	 * On peut aussi créer un Datagram sans fragment initial.
	 */
	public Datagram() {
		this("");
	}
	
	/**
	 * Ajoute un fragment au datagramme.
	 * @param fragment
	 */
	public void append(String fragment) {
		datagram += fragment;
	}
	
	/**
	 * Analyse le contenu du datagramme.
	 */
	public void parse() {
		StringTokenizer tok = new StringTokenizer(datagram, SEPARATOR);
		String token;
		boolean sunspot_id_found = false;
		int char_pos;
		while(tok.hasMoreTokens()) {
			token = tok.nextToken();
			
			if(!sunspot_id_found) {
				char_pos = token.indexOf(':');
				if(char_pos >= 0) {
					sunspotId = token.substring(char_pos+1).trim();
					sunspot_id_found = true;
				}
			}
			else {
				char_pos = token.indexOf('=');
				if(char_pos >= 0) {
					attributes.put(token.substring(0, char_pos),
							token.substring(char_pos+1).trim());
				}
			}
		}
	}
	
	/**
	 * Retourne l'identifiant du sunspot emmeteur du datagramme.
	 * @return Sunspot id
	 */
	public String getSunspotId() {
		return sunspotId;
	}
	
	/**
	 * Définit l'identifiant du sunspot emmeteur du datagramme.
	 * @param sunspot_id
	 */
	public void setSunspotId(String sunspot_id) {
		// Outdate datagram
		datagram = "";
		sunspotId = sunspot_id;
	}
	
	/**
	 * Retourne la valeur de l'attribut key contenue dans le datagramme.
	 * Si l'attribut n'est pas renseigné, une chaine vide est retournée.
	 * @param key
	 * @return attribute value (or an empty string)
	 */
	public String getAttribute(String key) {
		return attributes.containsKey(key) ? attributes.get(key) : "";
	}
	
	/**
	 * Définit la valeur d'un attribut contenu dans le datagramme.
	 * @param key
	 * @param value
	 * @return
	 */
	public void setAttribute(String key, String value) {
		// Outdate datagram
		datagram = "";
		attributes.put(key, value);
	}
	public void setAttribute(String key, int value) {
		setAttribute(key, Integer.toString(value));
	}
	public void setAttribute(String key, double value) {
		setAttribute(key, Double.toString(value));
	}
	
	/**
	 * Retourne la valeur d'un attribut contenu dans le datagramme sous la
	 * forme d'un entier.
	 * @param key
	 * @return Valeur | 0
	 */
	public int getAttributeAsInt(String key) {
		try {
			return attributes.containsKey(key) ?
					Integer.parseInt(attributes.get(key)) : 0;
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	/**
	 * Retourne la valeur d'un attribut contenu dans le datagramme sous la forme d'un
	 * nombre entier à double précision.
	 * @param key
	 * @return Valeur | 0
	 */
	public double getAttributeAsDouble(String key) {
		try {
			return attributes.containsKey(key) ?
					Double.parseDouble(attributes.get(key)) : 0.0;
		}
		catch(NumberFormatException e) {
			return 0.0;
		}
	}
	
	/**
	 * Génére si nécessaire et retourne le datagramme formé. 
	 * @return datagram
	 */
	public String getDatagramString() {
		if(datagram.isEmpty()) {
			datagram = "id_sunspot:" + sunspotId + ";";
			Iterator<Entry<String, String>> it = attributes.entrySet().iterator();
			while(it.hasNext()) {
				Entry<String,String> attr = it.next();
				datagram += attr.getKey() + "=" + attr.getValue() + ";";
			}
		}
		return datagram;
	}
	
	public String toString() {
		return getDatagramString();
	}
}
