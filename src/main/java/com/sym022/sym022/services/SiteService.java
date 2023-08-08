package com.sym022.sym022.services;

import com.sym022.sym022.entities.SiteEntity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class SiteService {

    /**
     * Method to find all sites
     * @param em
     * @return List of sites
     */
    public List<SiteEntity> findSiteAll (EntityManager em)
    {
        return em.createNamedQuery("Site.selectSiteAll", SiteEntity.class)
                .getResultList();
    }

    /**
     * Method to find a site by id
     * @param idSite
     * @param em
     * @return a site
     */
    public SiteEntity findSiteById(int idSite, EntityManager em)
    {
        return em.createNamedQuery("Site.selectSiteById", SiteEntity.class)
                .setParameter("idSite", idSite)
                .getSingleResult();
    }

    /**
     * Method to find a site by site number
     * @param siteNum
     * @param em
     * @return a site
     */
    public SiteEntity findSiteByNum(int siteNum, EntityManager em)
    {
        return em.createNamedQuery("Site.selectSiteByNum", SiteEntity.class)
                .setParameter("siteNum", siteNum)
                .getSingleResult();
    }

    /**
     * Method to find a site by site name
     * @param siteName
     * @param em
     * @return List of sites
     */
    public List<SiteEntity> findSiteByName(String siteName, EntityManager em)
    {
        return em.createNamedQuery("Site.selectListSiteBySiteName", SiteEntity.class)
                .setParameter("siteName", siteName)
                .getResultList();
    }

    /**
     * Method to find a site by investigator name
     * @param piName
     * @param em
     * @return List of sites
     */
    public List<SiteEntity> findSiteByPiName(String piName, EntityManager em)
    {
        return em.createNamedQuery("Site.selectListSiteByPiName", SiteEntity.class)
                .setParameter("piName", piName)
                .getResultList();
    }

    /**
     * Method to find sites opened
     * @param em
     * @return List of sites
     */
    public List<SiteEntity> findSiteByStatusTrue (EntityManager em)
    {
        return em.createNamedQuery("Site.selectListSiteByStatusTrue", SiteEntity.class)
                .getResultList();
    }

    /**
     * Method to find sites by characteristic
     * @param researchWord
     * @param em
     * @return List of sites
     */
    public List<SiteEntity> findSiteByFilter(String researchWord, EntityManager em){
        return em.createNamedQuery("Site.FindSiteByCharacteristic", SiteEntity.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to check if a Site Number already exists
     * @param siteNum
     * @param em
     * @return count of occurrence
     */
    public boolean isSiteExist(int siteNum, EntityManager em){
        Query query =em.createNamedQuery("Site.isSiteNumExist", SiteEntity.class);
        query.setParameter("siteNum", siteNum);

        int count =((Number)query.getSingleResult()).intValue();
        return count > 0;
    }

    /**
     * Method to add a new site
     * @param site
     * @param em
     * @return site
     */
    public SiteEntity addSite(SiteEntity site, EntityManager em){
        em.persist(site);
        em.flush();
        return site;
    }

    /**
     * Method to update a site
     * @param site
     * @param em
     * @return site
     */
    public SiteEntity updateSite(SiteEntity site, EntityManager em){
        em.merge(site);
        return site;
    }
}
