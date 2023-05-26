package com.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hibernate.model.Cliente;
import com.hibernate.util.HibernateUtil;


/**
 * Clase con funcionalidades para manejar los datos del cliente en la base de datos
 * @author a026577592b
 *
 */
public class ClienteDAO {

	
	/**
	 * Funcion para insertar el cliente en la base de datos
	 * @param c Cliente a insertar
	 */
	public static void insertCliente(Cliente c) {
		Transaction transaction = null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(c);
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * Funcion para actualizar los datos de un cliente en concreto
	 * @param c Cliente a actualizar
	 */
	public static void updateCliente(Cliente c) {
		Transaction transaction = null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(c);
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * Funcion para eliminar un cliente de la base de datos mediante un id
	 * @param id Id del cliente a eliminar
	 */
	public static void deleteCliente(int id) {
		Transaction transaction = null;
		Cliente c= null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			c=session.get(Cliente.class, id);
			session.remove(c);
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	
	/**
	 * funcion que selecciona un cliente en base a su id
	 * @param id Id del cliente a seleccionar
	 * @return Devuelve el cliente seleccionado con el id
	 */
	public static Cliente selectClienteByID(int id) {
		Transaction transaction = null;
		Cliente c = null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Query<Cliente> query = session.createQuery("FROM Cliente WHERE id = :id",Cliente.class);
			query.setParameter("id",id);
			c=query.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
		return c;
	}
	
	/**
	 * Funcion que devuelve en una lista todos los clientes de la base de datos
	 * @return Devuelve una lista con todos los clientes
	 */
	public static List<Cliente> selectAllClientes() {
		Transaction transaction = null;
		List<Cliente> clientes = null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			clientes=session.createQuery("from Cliente", Cliente.class).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if(transaction!=null) {
				transaction.rollback();
			}
		}
		return clientes;
	}
}
