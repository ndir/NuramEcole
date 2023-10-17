/**
 * 
 */
package com.ecole.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;


import com.chaka.projet.entity.Profile;
import com.chaka.projet.entity.Utilisateur;
import com.ecole.entity.Droits;
import com.ecole.entity.DroitsProfile;
import com.ecole.entity.Menu;
import com.ecole.entity.MenuParent;

/**
 * @author A626257
 * 
 */
@Name("droitsService")
@Scope(ScopeType.SESSION)
public class DroitsService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@In
	private Session dataSource;

	/**
	 * Utilisateur loggué
	 */
	@In(required = false)
	@Out(required = false)
	private Utilisateur utilisateur;

	private List<Utilisateur> listeUser = new ArrayList<Utilisateur>();

	private List<Profile> listeProfile = new ArrayList<Profile>();

	private List<Droits> listeDroits = new ArrayList<Droits>();

	private List<Droits> liste = new ArrayList<Droits>();

	private Droits droit = new Droits();

	private DroitsProfile droitp = new DroitsProfile();

	private List<DroitsProfile> listeDroitsP = new ArrayList<DroitsProfile>();

	private Utilisateur user = new Utilisateur();

	private List<Menu> listeMenu = new ArrayList<Menu>();

	private Profile profile = new Profile();

	private List<Droits> listeDroitsCon = new ArrayList<Droits>();

	private List<DroitsProfile> listeDroitsPCon = new ArrayList<DroitsProfile>();

	private List<MenuParent> listeMP = new ArrayList<MenuParent>();

	private MenuParent menuP = new MenuParent();

	private String showModal = "";

	public String versDroits() {
		chargerListeUtilisateur();
		return "/pages/nuramecole/creerdroits.xhtml";
	}

	public String versDroitsProfile() {
		chargerListeProfile();
		return "/pages/nuramecole/droitsprofile.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void chargerListeProfile() {
		listeProfile = new ArrayList<Profile>();
		listeProfile = dataSource.createQuery("From  Profile ").list();
		listeMP = new ArrayList<MenuParent>();
		listeMP = dataSource.createQuery("From MenuParent ").list();
	}

	@SuppressWarnings("unchecked")
	@Create
	public void chargerAllDroits() {
		listeDroitsCon = new ArrayList<Droits>();
		listeDroitsPCon = new ArrayList<DroitsProfile>();
		listeDroitsCon = dataSource
				.createQuery(
						"From Droits d inner join fetch d.user u inner join fetch d.menu  where u=:pu")
				.setParameter("pu", utilisateur).list();

		listeDroitsPCon = dataSource
				.createQuery(
						"From DroitsProfile d inner join fetch d.profile p inner join fetch d.menu  where p=:pp")
				.setParameter("pp", utilisateur.getProfile()).list();

	}

	// Afficher ou non un menu selon l'utilisateur connecte
	public boolean estAutorise(String menuButton) {
		boolean auth = false;
		for (Droits d : listeDroitsCon) {
			if (d.getMenu().getLibelle().equalsIgnoreCase(menuButton)) {
				auth = true;
				break;
			}
		}
		if (!auth) {
			for (DroitsProfile d : listeDroitsPCon) {
				if (d.getMenu().getLibelle().equalsIgnoreCase(menuButton)) {
					auth = true;
					break;
				}
			}
		}
		return auth;
	}

	// Afficher ou non un menu selon l'utilisateur connecte
	public boolean estAutoriseP(String menuButton) {
		boolean auth = false;
		MenuParent p = (MenuParent) dataSource
				.createQuery("From MenuParent where libelle =:plib")
				.setParameter("plib", menuButton).uniqueResult();
	
		for (Droits d : listeDroitsCon) {
			
			if (d.getMenu().getMenuparent().getId().equals(p.getId())) {
				auth = true;
				break;
			}
		}
		if (!auth) {
			for (DroitsProfile d : listeDroitsPCon) {
				
				if (d.getMenu().getMenuparent().getId().equals(p.getId())) {
					auth = true;
					break;
				}
			}
		}
		return auth;
	}

	@SuppressWarnings("unchecked")
	public void chargerListeUtilisateur() {
		listeUser = new ArrayList<Utilisateur>();
		listeUser = dataSource.createQuery(
				" From Utilisateur u inner join fetch u.profile  p ").list();
		listeMP = new ArrayList<MenuParent>();
		listeMP = dataSource.createQuery("From MenuParent ").list();
	}

	@SuppressWarnings("unchecked")
	public void listeDroitsParProfile(MenuParent parent) {
		listeMenu = new ArrayList<Menu>();
		listeMenu = dataSource
				.createQuery(
						" From Menu m inner join fetch m.menuparent p where p=:pp  ")
				.setParameter("pp", parent).list();
		listeDroitsP = new ArrayList<DroitsProfile>();
		listeDroitsP = dataSource
				.createQuery(
						"From DroitsProfile d inner join fetch d.profile p inner join fetch d.menu inner join fetch d.institution i "
						+ " where p=:pp and i=:pi")
				.setParameter("pp", profile).setParameter("pi", utilisateur.getInstitution()).list();

		if (listeDroitsP.size() > 0) {
			for (DroitsProfile d : listeDroitsP) {
				for (Menu m : listeMenu) {
					if (d.getMenu().getId().equals(m.getId()))
						m.setChoix(true);
				}
			}
		}
		this.setShowModal("javascript:Richfaces.showModalPanel('droits',{width:250, top:50, height:510})");
	}

	@SuppressWarnings("unchecked")
	public void listeDroitsByUser(MenuParent parent) {
		listeMenu = new ArrayList<Menu>();
		listeMenu = dataSource
				.createQuery(
						" From Menu m inner join fetch m.menuparent p where p=:pp  ")
				.setParameter("pp", parent).list();
		liste = new ArrayList<Droits>();
		liste = dataSource
				.createQuery(
						"From Droits d inner join fetch d.user u inner join fetch d.menu  where u=:pu")
				.setParameter("pu", user).list();
		if (liste.size() > 0) {
			for (Droits d : liste) {
				for (Menu m : listeMenu) {
					if (d.getMenu().getId().equals(m.getId()))
						m.setChoix(true);
				}
			}
		}
		listeDroitsP = new ArrayList<DroitsProfile>();
		listeDroitsP = dataSource
				.createQuery(
						"From DroitsProfile d inner join fetch d.profile p inner join fetch d.menu  where p=:pp")
				.setParameter("pp", user.getProfile()).list();
		if (listeDroitsP.size() > 0) {
			for (DroitsProfile d : listeDroitsP) {
				for (Menu m : listeMenu) {
					if (d.getMenu().getId().equals(m.getId()))
						m.setChoix(true);
				}
			}
		}
		this.setShowModal("javascript:Richfaces.showModalPanel('droits',{width:250, top:50, height:410})");
	}

	public boolean menuExiste(Menu menu, List<Droits> liste) {
		boolean existe = false;
		for (Droits d : liste) {
			if (d.getMenu().getId().equals(menu.getId())) {
				existe = true;
				droit = (Droits) dataSource.get(Droits.class, d.getId());
				break;
			}
		}
		return existe;
	}

	public boolean menuExisteProfile(Menu menu, List<DroitsProfile> liste) {
		boolean existe = false;
		for (DroitsProfile d : liste) {
			if (d.getMenu().getId().equals(menu.getId())) {
				existe = true;
				droitp = (DroitsProfile) dataSource.get(DroitsProfile.class,
						d.getId());
				break;
			}
		}
		return existe;
	}

	// Affectation des droits
	@SuppressWarnings("unchecked")
	public void ajouterDroits() {
		if (user == null) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"erreurGenerique", "Veuillez chosir un utilisateur");
			return;
		}

		for (Menu m : listeMenu) {
			if (m.isChoix()) {
				Droits d = new Droits();
				d.setMenu(m);
				d.setUser(user);
				dataSource.saveOrUpdate(d);
			}
			if (!m.isChoix()) {
				for (Droits d : liste) {
					if (d.getMenu().equals(m)) {
						dataSource.delete(d);
						break;
					}
				}

			}
		}
		this.setShowModal("javascript:Richfaces.hideModalPanel('droits',{width:600, top:50, height:510})");

		// for (Menu m : listeMenu) {
		// if (m.isChoix() && !menuExiste(m, liste)) {
		// Droits d = new Droits();
		// d.setMenu(m);
		// d.setUser(user);
		// dataSource.save(d);
		// } else {
		// if (menuExiste(m, liste) && !m.isChoix()) {
		// dataSource.delete(droit);
		// }
		// }
		// }
		this.setUser(new Utilisateur());
		listeMenu = new ArrayList<Menu>();
		FacesMessages.instance().addToControlFromResourceBundle(
				"infoGenerique", "Droits affectés avec succés");

	}

	@SuppressWarnings("unchecked")
	public void ajouterDroitsProfile() {
		if (profile == null) {
			FacesMessages.instance().addToControlFromResourceBundle(
					"erreurGenerique", "Veuillez chosir un profile");
			return;
		}
		for (Menu m : listeMenu) {
			if (m.isChoix()) {
				DroitsProfile d = new DroitsProfile();
				d.setMenu(m);
				d.setInstitution(utilisateur.getInstitution());
				d.setProfile(profile);
				dataSource.saveOrUpdate(d);
			}
			if (!m.isChoix()) {
				for (DroitsProfile d : listeDroitsP) {
					if (d.getMenu().equals(m)) {
						dataSource.delete(d);
						break;
					}
				}

			}
		}
		this.setProfile(new Profile());
		listeMenu = new ArrayList<Menu>();
		FacesMessages.instance().addToControlFromResourceBundle(
				"infoGenerique", "Droits affectés avec succés");
	}

	public List<Utilisateur> getListeUser() {
		return listeUser;
	}

	public void setListeUser(List<Utilisateur> listeUser) {
		this.listeUser = listeUser;
	}

	public List<Droits> getListeDroits() {
		return listeDroits;
	}

	public void setListeDroits(List<Droits> listeDroits) {
		this.listeDroits = listeDroits;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	public List<Menu> getListeMenu() {
		return listeMenu;
	}

	public void setListeMenu(List<Menu> listeMenu) {
		this.listeMenu = listeMenu;
	}

	public List<DroitsProfile> getListeDroitsP() {
		return listeDroitsP;
	}

	public void setListeDroitsP(List<DroitsProfile> listeDroitsP) {
		this.listeDroitsP = listeDroitsP;
	}

	public List<Profile> getListeProfile() {
		return listeProfile;
	}

	public void setListeProfile(List<Profile> listeProfile) {
		this.listeProfile = listeProfile;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<MenuParent> getListeMP() {
		return listeMP;
	}

	public void setListeMP(List<MenuParent> listeMP) {
		this.listeMP = listeMP;
	}

	public MenuParent getMenuP() {
		return menuP;
	}

	public void setMenuP(MenuParent menuP) {
		this.menuP = menuP;
	}

	public String getShowModal() {
		return showModal;
	}

	public void setShowModal(String showModal) {
		this.showModal = showModal;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
