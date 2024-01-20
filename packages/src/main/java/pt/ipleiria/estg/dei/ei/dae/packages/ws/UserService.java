package pt.ipleiria.estg.dei.ei.dae.packages.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.mail.MessagingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.packages.dtos.EmailDTO;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.EmailBean;
import pt.ipleiria.estg.dei.ei.dae.packages.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.dae.packages.entities.User;
import pt.ipleiria.estg.dei.ei.dae.packages.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.packages.security.Authenticated;

@Path("users")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class UserService {

    @EJB
    private UserBean userBean;

    @EJB
    private EmailBean emailBean;

    @POST
    @Path("/{username}/email/send")
    @RolesAllowed({"Manufacturer", "Customer", "LogisticsOperator"})
    public Response sendEmail(@PathParam("username") String username, EmailDTO email)
            throws MyEntityNotFoundException, MessagingException {
        User user = userBean.find(username);
        if (user == null) {
            throw new MyEntityNotFoundException("Student with username '" + username
                    + "' not found in our records.");
        }
        emailBean.send(user.getEmail(), email.getSubject(), email.getMessage());
        return Response.status(Response.Status.OK).entity("E-mail sent").build();
    }
}
