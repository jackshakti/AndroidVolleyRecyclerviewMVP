package shakti.volleymvprecyclerview.app.model.pojo;

/**
 * Created by Sakthivel on 15-12-2019.
 */
public class RecyclerviewPojo {

    private String id, projectName, sProjectImage, sDescription;
    private String investedInvestor, projectStage;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getsProjectImage() {
        return sProjectImage;
    }

    public void setsProjectImage(String sProjectImage) {
        this.sProjectImage = sProjectImage;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public String getInvestedInvestor() {
        return investedInvestor;
    }

    public void setInvestedInvestor(String investedInvestor) {
        this.investedInvestor = investedInvestor;
    }

    public String getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(String projectStage) {
        this.projectStage = projectStage;
    }

}
