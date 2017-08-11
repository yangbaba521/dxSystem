package signup;

/**
 * Created by sunyuyang on 17/4/11.
 */
public class userInfo {
    private String company[][] = {{"jsdy","江苏多元业务部"}};
    private String partment[][] = {{"zjls","总经理室"},{"dynq","多元内勤"}};

    public String getCompany(String code){
        for(int i=0; i<company.length; i++){
            if(code.equals(company[i][0])){
                return company[i][1];
            }
        }
        return "null";
    }

    public String getPartment(String code){
        for(int i=0; i<partment.length; i++){
            if(code.equals(partment[i][0])){
                return partment[i][1];
            }
        }
        return "null";
    }
}
