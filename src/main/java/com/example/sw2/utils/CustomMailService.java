package com.example.sw2.utils;

import com.example.sw2.entity.AsignadosSedes;
import com.example.sw2.entity.Inventario;
import com.example.sw2.entity.Usuarios;
import com.example.sw2.entity.Ventas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.sw2.constantes.CustomConstants.MANAGER_EMAIL;

@Component
public class CustomMailService {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail() {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("to_1@gmail.com", "to_2@gmail.com", "to_3@yahoo.com");

		msg.setSubject("Testing from Spring Boot");
		msg.setText("Hello World \n Spring Boot Email");

		javaMailSender.send(msg);

	}

	public void sendTestEmailWithAtachment() throws MessagingException, IOException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		// true = multipart message
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo("gustavomeza27@gmail.com");
		helper.setSubject("Testing from Spring Boot");
		// default = text/plain
		//helper.setText("Check attachment for image!");
		// true = text/html
		helper.setText("<h1>Check attachment for image!</h1>", true);
		FileSystemResource file = new FileSystemResource(new File("/Users/Gustavo_Meza/Desktop/aaa/test.txt"));
		helper.addAttachment("test.txt", file);
		javaMailSender.send(msg);

	}

	public void sendEmailWithAtachment(String to, String subject, String title, String message, FileSystemResource file) throws MessagingException, IOException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText("<html> <body>" +
				"<h1>"+title+"</h1> " +
				"<p>"+message+" </p>" +
				"</body></html>", true);
		helper.addAttachment(file.getFilename(), file);
		javaMailSender.send(msg);
	}

	public void sendEmail(String to, String subject, String title, String message) throws MessagingException, IOException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText("<html> <body>" +
				"<h1>"+title+"</h1> " +
				"<p>"+message+" </p>" +
				"</body></html>", true);
		//helper.setText(message);
		javaMailSender.send(msg);
	}

	public void sendEmail2(String to, String subject, String title, String message) throws MessagingException, IOException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText("<html> <body>" +
				"<h1>"+title+"</h1> "
				+message+
				"</body></html>", true);
		//helper.setText(message);
		javaMailSender.send(msg);
	}

	public void sendEmailPassword(Usuarios u) throws MessagingException, IOException {
		sendEmail(u.getCorreo(),"Confirmación de cuenta Mosqoy",
				"Bienvenido " + u.getFullname() + ", usuario "+u.getRoles().getNombrerol().toUpperCase(),
				"Este es un mensaje de confirmación de cuenta, para ingresar al sistema use la siguiente contraseña:\n"+u.generateNewPassword());
	}

	public void sendStockAlert(AsignadosSedes a) throws IOException, MessagingException {
		String subject = "Mosqoy - Stock agotado en sede";
		String title = "Alerta por stock agotado  por ventas";
		String message = "";
		message+="<p>Se ha agotado el stock del producto de inventario con código <b>"+a.getId().getProductoinventario().getCodigoinventario()
				+"</b>, para el usuario sede <b>"+a.getId().getSede().getFullname()+"</b>.<br></p>";
		message+="<p>Si desea comunicarse con el usuario, su correo es: "+a.getId().getSede().getCorreo()+" y " +
				"su numero celular: "+a.getId().getSede().getTelefono()+"</p>";
		String to = a.getId().getGestor().getCorreo();
		sendEmail(to,subject,title,message);
	}

	public void sendProductExpiration(List<Inventario> list, String[] emails) throws MessagingException {
		int ld = LocalDate.now().getMonthValue();
		StringBuilder message= new StringBuilder();
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(emails);
		helper.setSubject("Mosqoy - Correo mensual de vencimiento de productos");

		list.forEach(l->{
			if(l.getFechavencimientoconsignacion()==null || (l.getFechavencimientoconsignacion().getMonthValue()!=ld)){
				list.remove(l);
			}
		});

		if (list.isEmpty()){
			message.append("<p>No se encontraron productos con fecha de vencimiento para este mes</p>");
		}else {
			message.append("<table>\n" +
					"<thead>\n" +
					"<th>Codigo</th>\n" +
					"<th>Fecha de vencimiento</th>\n" +
					"</thead>\n" +
					"<tbody>\n");
			for (Inventario inv : list){
				message.append("<tr>\n" + "<td>")
						.append(inv.getCodigoinventario())
						.append("</td>\n").append("<td>")
						.append(inv.getFechavencimientoconsignacion())
						.append("</td>\n").append(" </tr>\n");
			}
			message.append("</tbody>\n" + "  </table>");
		}


		helper.setText("<html> <body>" +
				"<h1>Fechas de vencimiento de productos en Inventario</h1> "
				+message+
				"</body></html>", true);
		javaMailSender.send(msg);

	}


	public void sendSaleConfirmation(Ventas v) throws IOException, MessagingException {
		String msg ;
		String table;
		String msg2="";
		if(v.getConfirmado()) { // Venta confirmada
			msg="<p>El usuario "+v.getVendedor().getRoles().getNombrerol()+" "+v.getVendedor().getFullname()+
			", ha registrado una venta en el sistema. Los detalles de la venta se muestran a continuación</p>";

			if (v.getMedia()!=null && v.getMedia().length()>10){
				msg2+="<p>Adicionalmente, el usuario registro un archivo del comprobante de pago, el cual puede" +
						" visualizar a través del siguiente enlace: <br> <a href='"+v.getMedia()+"'>"+v.getMedia()+"</a></p>";
			}

			msg2+="<p>Si desea comunicarse con el usuario, su correo es: "+v.getVendedor().getCorreo()+" y " +
					"su numero celular: "+v.getVendedor().getTelefono()+"</p>";
			table = "<table>\n" +
					"            <thead>\n" +
					"              <th>Nombre del cliente</th>\n" +
					"              <th>Tipo de comprobante</th>\n" +
					"              <th>Numero de comprobante</th>\n" +
					"              <th>Lugar de venta</th>\n" +
					"              <th>Codigo inventario</th>\n" +
					"              <th>Cantidad</th>\n" +
					"              <th>Precio unitario</th>\n" +
					"              <th>Total</th>\n" +
					"              <th>Fecha</th>\n" +
					"            </thead>\n" +
					"            <tbody>\n" +
					"              <tr>\n" +
					"                <td>"+v.getNombrecliente()+"</td>\n" +
					"                <td>"+v.getId().getNombreTipodocumento()+"</td>\n" +
					"                <td>"+v.getId().getNumerodocumento()+"</td>\n" +
					"                <td>"+v.getLugarventa()+"</td>\n" +
					"                <td>"+v.getInventario().getCodigoinventario()+"</td>\n" +
					"                <td>"+v.getCantidad()+"</td>\n" +
					"                <td>"+v.getPrecioventa()+"</td>\n" +
					"                <td>"+v.getSumaParcial()+"</td>\n" +
					"                <td>"+v.getFecha()+"</td>\n" +
					"              </tr>\n" +
					"            </tbody>\n" +
					"          </table>";
			sendEmail2(MANAGER_EMAIL, "Mosqoy - Venta confirmada",
					"Se ha registrado una venta por parte de " + v.getVendedor().getFullname(),
					msg+table+msg2);

		}
		else {// Solicitud de comprobante
			msg ="<p>El usuario sede, ha solicitado un(a) "+ v.getId().getNombreTipodocumento() + " para poder concluir " +
					"la venta detallada a continuación</p>";

			msg2+="<p>Si desea comunicarse con el usuario, su correo es: "+v.getVendedor().getCorreo()+" y " +
					"su numero celular: "+v.getVendedor().getTelefono()+"</p>";

			table = "<table>\n" +
					"            <thead>\n" +
					"              <th>Nombre del cliente</th>\n" +
					"              <th>Tipo de comprobante</th>\n" +
					"              <th>Lugar de venta</th>\n" +
					"              <th>Codigo inventario</th>\n" +
					"              <th>Cantidad</th>\n" +
					"              <th>Precio unitario</th>\n" +
					"              <th>Total</th>\n" +
					"              <th>Fecha</th>\n" +
					"            </thead>\n" +
					"            <tbody>\n" +
					"              <tr>\n" +
					"                <td>"+v.getNombrecliente()+"</td>\n" +
					"                <td>"+v.getId().getNombreTipodocumento()+"</td>\n" +
					"                <td>"+v.getLugarventa()+"</td>\n" +
					"                <td>"+v.getInventario().getCodigoinventario()+"</td>\n" +
					"                <td>"+v.getCantidad()+"</td>\n" +
					"                <td>"+v.getPrecioventa()+"</td>\n" +
					"                <td>"+v.getSumaParcial()+"</td>\n" +
					"                <td>"+v.getFecha()+"</td>\n" +
					"              </tr>\n" +
					"            </tbody>\n" +
					"          </table>";
			sendEmail2(MANAGER_EMAIL, "Mosqoy - Solicitud de comprobante de pago",
					v.getVendedor().getFullname() + " ha solicitado un comprobante de pago (<strong>"+v.getId().getNombreTipodocumento()+ "</strong>)",
					msg+table+msg2);
		}
	}

}


