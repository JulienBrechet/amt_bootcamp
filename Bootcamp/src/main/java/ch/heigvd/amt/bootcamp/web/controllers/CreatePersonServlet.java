package ch.heigvd.amt.bootcamp.web.controllers;



import ch.heigvd.amt.bootcamp.model.Person;
import ch.heigvd.amt.bootcamp.services.dao.PeopleWriteManagerLocal;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CreatePersonServlet extends HttpServlet {
   
  @EJB
  PeopleWriteManagerLocal peopleWriteManager;

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    

    try{
         //we check if we arrived here after the creation of the person
         //otherwise there is an exception nd we catch it to go create this person
         int code = Integer.parseInt(request.getParameter("code"));
         
         //we get the new values to create
         String firstName = request.getParameter("firstName");
         String lastName = request.getParameter("lastName");
         String street = request.getParameter("street");
         
         //we build the new person
         Person newPerson = new Person(0, firstName, lastName, street);
         //we put it in the list of people to write in DB
         List<Person> peopleToWrite = new LinkedList<>();
         peopleToWrite.add(newPerson);
         
         //we write it in the DB
         peopleWriteManager.writePeople(peopleToWrite);
         
         //redirect to ManageServelet
         String targetUrl = "/pages/manage";
         targetUrl = request.getContextPath() + targetUrl;
         response.sendRedirect(targetUrl);
         
         
         
    }catch(NumberFormatException ex){
       
        request.getRequestDispatcher("/WEB-INF/pages/create.jsp").forward(request, response);
    }

  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
