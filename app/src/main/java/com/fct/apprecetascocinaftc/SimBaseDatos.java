package com.fct.apprecetascocinaftc;

import com.fct.apprecetascocinaftc.Modelo.Usuario;

import java.util.ArrayList;

public class SimBaseDatos {
        private ArrayList<Usuario> data;

        public SimBaseDatos() {
            data = new ArrayList<Usuario>();
            data.add(new Usuario(1, "JoseP", "1234", "josee@gmail.com"));
            data.add(new Usuario(2, "Rau2", "1234", "rau2@gmail.com"));
            data.add(new Usuario(3, "JmAbarca", "1234", "jmabarca@gmail.com"));
        }

        public void add(Usuario record) {
            data.add(record);
        }

        public void delete(int index) {
            data.remove(index);
        }

        public Usuario get(int index) {
            return data.get(index);
        }

        public void update(int index, Usuario record) {
            data.set(index, record);
        }

        public int size() {
            return data.size();
        }
    }
