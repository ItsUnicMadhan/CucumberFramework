package com.expleo.model;

import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRepoReq {
	private String name;
    private String description;
    private String homepage;
    @JsonProperty("private") 
    private boolean myprivate;
    private boolean has_issues;
    private boolean has_projects;
    private boolean has_wiki;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public boolean isMyprivate() {
		return myprivate;
	}
	public void setMyprivate(boolean myprivate) {
		this.myprivate = myprivate;
	}
	public boolean isHas_issues() {
		return has_issues;
	}
	public void setHas_issues(boolean has_issues) {
		this.has_issues = has_issues;
	}
	public boolean isHas_projects() {
		return has_projects;
	}
	public void setHas_projects(boolean has_projects) {
		this.has_projects = has_projects;
	}
	public boolean isHas_wiki() {
		return has_wiki;
	}
	public void setHas_wiki(boolean has_wiki) {
		this.has_wiki = has_wiki;
	}
    
    
}
