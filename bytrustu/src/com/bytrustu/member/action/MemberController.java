package com.bytrustu.member.action;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

@WebServlet("*.do")
public class MemberController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private HashMap<String, Action> commandMap;

  public MemberController() {
    super();
  }

  
  // 서블릿 호출시 init 부터 실행 , 이동 될 경로에 해당되는 HashMap key values 값이 저장 된 properties 파일을 로드
  public void init(ServletConfig config) throws ServletException {
    loadProperties("com.bytrustu.member.properties/MemberCommand");
  }

  
  // properties 파일에서 키값과 value 값인 클래스 정보를 추출해서 그것을 HashMap에 저장한다.
  private void loadProperties(String filePath) {
    commandMap = new HashMap<String, Action>();
    ResourceBundle rb = ResourceBundle.getBundle(filePath);
    
    // 키값을 가져온다.
    Enumeration<String> actionEnum = rb.getKeys();

    while (actionEnum.hasMoreElements()) {
      
      // 명령어를 가져온다.
      String command = actionEnum.nextElement();
      
      // 명령어에 해당하는 Action 클래스 이름을 가져온다.
      String className = rb.getString(command);

      
      try {
        // 이름명으로 클래스 생성
        Class<?> actionClass = Class.forName(className);
        Action actionInstance = (Action) actionClass.newInstance();

        if (className.equals("com.bytrustu.member.action.MemberFormChangeAction")) {
          MemberFormChangeAction mf = (MemberFormChangeAction) actionInstance;
          mf.setCommand(command);
        }
        commandMap.put(command, actionInstance);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   /* System.out.println("doGet");*/
    actionDo(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  /*  System.out.println("doPost");*/
    actionDo(request, response);
  }

  private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  /*  System.out.println("actionDo");*/

    request.setCharacterEncoding("UTF-8");
    
    String uri = request.getRequestURI();
    int conPath = uri.lastIndexOf("/") + 1;
    String command = uri.substring(conPath);

    /*System.out.println("command >>>>>>>>>>" + command);*/

    ActionForward forward = null;
    Action action = null;

    try {
      // 넘어온 커맨드 이름만 추출하는 과정
      action = commandMap.get(command);

      // HashMap 에서 명령어에 해당하는 Action을 가져온다.
      if (action == null) {
        System.out.println("command : " + command + "는 잘못된 명령어 입니다.");
        return;
      }
      
      forward = action.execute(request, response);
      

      // 이동방식 처리
      if (forward != null) {
    	// true 인 경우 sendRedirect 이용 ( 정보 값 없이 이동 )
        if (forward.isRedirect()) {
          response.sendRedirect(forward.getNextPath());
          
          // false 인 경우 RequestDispatcher 이용 ( 정보 값을 가지고 이동 )
        } else {
          RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getNextPath());
          dispatcher.forward(request, response);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  } // end actionDo

}
