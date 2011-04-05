package network;

import network.Datagram;

public class DatagramTest extends tester.Tester
{
	public static void main(String[] args) {
		t = new DatagramTest();
		test();
	}
	
	public void _run() {
		Datagram d;
	
		/** Test 1 : Obtenir un datagramme en plusieurs fragments donne un datagramme correct */
		d = new Datagram("sunspot_id:sun");
		d.append("SPOT_12 ;");
		d.append("temperature=12.5;batt");
		d.append("ery=56;;");
		assertTrue(
			d.getDatagramString().equals("sunspot_id:sunSPOT_12 ;temperature=12.5;battery=56;;")
		);
		
		/** Test 2 : Parse un datagramme */
		d.parse();
		assertTrue(d.getSunspotId().equals("sunSPOT_12"));
		assertTrue(d.getAttribute("battery").equals("56"));
		assertTrue(d.getAttributeAsInt("battery") == 56);
		assertTrue(d.getAttributeAsDouble("temperature") == 12.5);
		
		/** Test 3 : Génére un datagramme */
		d = new Datagram();
		d.setSunspotId("sunSPOT_12");
		d.setAttribute("temperature", 12.5);
		d.setAttribute("battery", 56);
		assertTrue(d.toString().equals("id_sunspot:sunSPOT_12;battery=56;temperature=12.5;")
				|| d.toString().equals("id_sunspot:sunSPOT_12;temperature=12.5;battery=56;"));
		d.setAttribute("temperature", "15");
		assertTrue(d.toString().equals("id_sunspot:sunSPOT_12;battery=56;temperature=15;") ||
				d.toString().equals("id_sunspot:sunSPOT_12;temperature=15;battery=56;"));
	}
	
}
