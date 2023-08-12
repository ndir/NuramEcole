SELECT
     fd.totalHT,fd.totalTTC,fd.taux,fd.totalHeure,fd.tva,tp.libTypePrestation,fd.idPlanAide
FROM
     `facture` f,
     `facturedetail` fd,
     `beneficiaire` b,
     `lsttypeprestation` tp	
WHERE
     f.idfacture = fd.idfacture
     and f.idbeneficiaire = 5
     and f.mois = 6
     and f.annee = 2010
     and b.idbeneficiaire = f.idbeneficiaire
     and fd.idtypeprestation=tp.idtypeprestation