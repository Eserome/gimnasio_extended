package com.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hibernate.model.CE;
import com.hibernate.model.Ejercicio;
import com.hibernate.util.HibernateUtil;



public class ClienteEjercicioDAO {

	
	
	public static void insertClienteEjercicio(CE ce) {
		Transaction transaction = null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(ce);
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	public static void deleteCE(CE ce) {
		Transaction transaction = null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.remove(ce);
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	public static void updateEjercicio(CE ce) {
		Transaction transaction = null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(ce);
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	public static void deleteClienteEjercicio(int id) {
		Transaction transaction = null;
		Ejercicio ej= null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			ej=session.get(Ejercicio.class, id);
			session.remove(ej);
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	public static List<CE> selectEjerciciosByClienteID(int cliente_id) {
		Transaction transaction = null;
		List<CE> ejercicios = null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Query<CE> query = session.createQuery("FROM CE WHERE cliente_id = :cliente_id",CE.class);
			query.setParameter("cliente_id",cliente_id);
			ejercicios = query.getResultList();
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
		return ejercicios;
	}
	
	public static CE selectCEbyIDS(int cliente_id, int ejercicio_id) {
		Transaction transaction = null;
		CE ce = null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Query<CE> query = session.createQuery("FROM CE WHERE cliente_id = :cliente_id AND id_ejercicio = :ejercicio_id",CE.class);
			query.setParameter("cliente_id",cliente_id);
			query.setParameter("ejercicio_id",ejercicio_id);
			ce=query.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
		return ce;
	}
	
	
	public static List<CE> selectAllCES() {
		Transaction transaction = null;
		List<CE> ejercicios = null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			ejercicios=session.createQuery("FROM CE", CE.class).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
		return ejercicios;
	}
}
