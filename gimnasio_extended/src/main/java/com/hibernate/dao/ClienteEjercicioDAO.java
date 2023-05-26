package com.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hibernate.model.CE;
import com.hibernate.model.Ejercicio;
import com.hibernate.util.HibernateUtil;


/**
 * Clase con funciones para manejar la tabla de las rutinas(ClientesEjercicios)
 * @author a026577592b
 *
 */
public class ClienteEjercicioDAO {

	
	/**
	 * Funcion para insertar una rutina en la tabla ClientesEjercicios
	 * @param ce Rutina a insertar
	 */
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
	
	/**
	 * Funcion para eliminar una rutina de la base de datos
	 * @param ce Rutina a borrar de la base de datos
	 */
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
	
	/**
	 * Funcion para actualizar una rutina de la base de datos 
	 * @param ce Rutina a actualizar
	 */
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
	
	/**
	 * Funcion que elimina una rutina en base al id de un ejercicio
	 * @param id Id del ejercicio que esté en la rutina para eliminar
	 */
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
	
	/**
	 * Funcion que selecciona todos los ejercicios que tiene un cliente en su rutina
	 * @param cliente_id Id del cliente a consultar sus ejercicios
	 * @return Devuelve una lista con las rutinas del cliente id filtrado
	 */
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
	
	/**
	 * Funcion que devuelve una rutina en base a un cliente id y un ejercicio id
	 * @param cliente_id Id del cliente de la rutina
	 * @param ejercicio_id Id del ejercicio de la rutina
	 * @return Devuvel la rutina con el cliente id y el ejercicio id
	 */
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
	
	/**
	 * Funcion que selecciona todas las rutinas de la base de datos
	 * @return Devuelve una lista con todas las rutinas
	 */
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
