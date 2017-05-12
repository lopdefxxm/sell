package aero.framework.domain;

import java.util.List;

public class SpecialUrcAccessDefinition {
	
	private List<String> specialUrlList = null;
	
	private List<String> specialFileList = null;
	
	public List<String> getSpecialUrlList() {
		return specialUrlList;
	}

	public void setSpecialUrlList(List<String> specialUrlList) {
		this.specialUrlList = specialUrlList;
	}

	public List<String> getSpecialFileList() {
		return specialFileList;
	}

	public void setSpecialFileList(List<String> specialFileList) {
		this.specialFileList = specialFileList;
	}
	
	
	public boolean isSpecialUrl(String url){
		for(String surl:specialUrlList){
			if(url.equals(surl)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isSpecialFile(String file){
		for(String suffix:specialFileList){
			if(file.endsWith("."+suffix)){
				return true;
			}
		}
		return false;
	}

}
