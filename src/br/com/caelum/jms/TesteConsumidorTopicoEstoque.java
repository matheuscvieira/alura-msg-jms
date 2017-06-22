package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class TesteConsumidorTopicoEstoque {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		//JMS 1.1 - ActiveMQ ainda não dá suporte ao JMS 2.0
		InitialContext context = new InitialContext();		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");		
		Connection connection = factory.createConnection("user", "senha");
		connection.setClientID("estoque");
		connection.start();
		
		// param1: desejo ter uma transação?
		// param2: metodo para confirmação do recebimento de uma mensagem
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//Destination topico = (Destination) context.lookup("loja");
		Topic topico = (Topic) context.lookup("loja");
		//MessageConsumer consumer = session.createConsumer(topico);
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura");
		
		// Receive recebe 1 mensagem, processa e finaliza o programa
		// Caso seja necessario consumir mais de uma msg via receive() - receive apenas para 1 mensagem
		//Message message1 = consumer.receive();
		//Message message2 = consumer.receive();
		//Message message3 = consumer.receive();
		//Message message4 = consumer.receive();
		
		//consumer recebe a mensagem e delega o tratamento para um listener
		consumer.setMessageListener(new MessageListener() {			
			@Override
			public void onMessage(Message message) {	
				TextMessage textMessage = (TextMessage) message;
				//System.out.println("Recebendo msg: " + message);
				try {
					System.out.println("Recebendo msg: " + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		//System.out.println("Recebendo msg: " + message1);
		
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();

	}

}
