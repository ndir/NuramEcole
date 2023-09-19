/**
 * 
 */
package com.ecole.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;

import com.ecole.entity.Menu;
import com.ecole.entity.MenuParent;



/**
 * @author A626257
 * 
 */
@Name("menuSerivice")
@Scope(ScopeType.SESSION)
public class MenuSerivice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Menu menu = new Menu();

	private List<Menu> listeMenu = new ArrayList<Menu>();

	private MenuParent menuP = new MenuParent();

	private List<MenuParent> listeMenuP = new ArrayList<MenuParent>();

	@In
	private Session dataSource;

	public String versMenu() {
		this.setMenu(new Menu());
		this.setMenuP(new MenuParent());
		chargerListeMenu();
		return "/pages/nuramecole/menu.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void chargerListeMenu() {
		listeMenu = new ArrayList<Menu>();
		listeMenu = dataSource.createQuery(
				" From Menu m left outer join fetch m.menuparent").list();
		listeMenuP = new ArrayList<MenuParent>();
		listeMenuP = dataSource.createQuery("From MenuParent").list();
	}

	public void ajouterMenu() {
		if (menu.getId() == null) {
			dataSource.save(menu);
		} else {
			dataSource.update(menu);
		}
		this.setMenu(new Menu());
		chargerListeMenu();
		FacesMessages.instance().addToControlFromResourceBundle(
				"infoGenerique", "Ok");

	}

	public void ajouterMenuParent() {
		if (menuP.getId() == null) {
			dataSource.save(menuP);
		} else {
			dataSource.update(menuP);
		}
		this.setMenuP(new MenuParent());
		chargerListeMenu();
		FacesMessages.instance().addToControlFromResourceBundle(
				"infoGenerique", "Ok");

	}
	
	
	public void versModifierParent(MenuParent menu) {
		this.setMenuP(menu);
	}

	
	public void versModifier(Menu menu) {
		this.setMenu(menu);
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Menu> getListeMenu() {
		return listeMenu;
	}

	public void setListeMenu(List<Menu> listeMenu) {
		this.listeMenu = listeMenu;
	}

	public MenuParent getMenuP() {
		return menuP;
	}

	public void setMenuP(MenuParent menuP) {
		this.menuP = menuP;
	}

	public List<MenuParent> getListeMenuP() {
		return listeMenuP;
	}

	public void setListeMenuP(List<MenuParent> listeMenuP) {
		this.listeMenuP = listeMenuP;
	}

}
