package com.yusys.agile.excel.domain;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public class CheckResult {

	private Workbook wb;
	private boolean hasError;
	private boolean isTemplate;
	private List<Mistake> mistakes;


	public Workbook getWb() {
		return wb;
	}

	public void setWb(Workbook wb) {
		this.wb = wb;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public boolean isTemplate() {
		return isTemplate;
	}

	public void setTemplate(boolean isTemplate) {
		this.isTemplate = isTemplate;
	}

	public List<Mistake> getMistakes() {
		return mistakes;
	}

	public void setMistakes(List<Mistake> mistakes) {
		this.mistakes = mistakes;
	}

}
