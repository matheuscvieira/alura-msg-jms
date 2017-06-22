package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorTopico {

	public static void main(String[] args) throws Exception {
		
		//JMS 1.1 - ActiveMQ ainda não dá suporte ao JMS 2.0
		InitialContext context = new InitialContext();		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");		
		Connection connection = factory.createConnection("user", "senha");
		connection.start();
		
		// param1: desejo ter uma transação?
		// param2: metodo para confirmação do recebimento de uma mensagem
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination topico = (Destination) context.lookup("loja");		
		MessageProducer producer = session.createProducer(topico);

		Message message = session.createTextMessage("<pedido><id>333</id></pedido>");
		//message.setBooleanProperty("ebook", true);
		producer.send(message);
		
		session.close();
		connection.close();
		context.close();

	}

}
