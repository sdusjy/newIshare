package org.google.sdu.douban.movie;

public class Movie {
	private String singleUrl;
	private String imgUrl;
	private String title;
	private float rating;
	private String director;
	private String cast;
	private String area;
	private String reDate;

	public Movie() {

	}

	public Movie(String singleUrl, String imgUrl, String title, float rating,
			String director, String cast, String area, String reDate) {
		this.singleUrl = singleUrl;
		this.imgUrl = imgUrl;
		this.title = title;
		this.rating = rating;
		this.director = director;
		this.cast = cast;
		this.area = area;
		this.reDate = reDate;
	}

	public String getSingleUrl() {
		return singleUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public String getTitle() {
		return title;
	}

	public String getDirector() {
		return director;
	}

	public String getCast() {
		return cast;
	}

	public String getArea() {
		return area;
	}

	public void setSingleUrl(String singleUrl) {
		this.singleUrl = singleUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getReDate() {
		return reDate;
	}

	public void setReDate(String reDate) {
		this.reDate = reDate;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

}
