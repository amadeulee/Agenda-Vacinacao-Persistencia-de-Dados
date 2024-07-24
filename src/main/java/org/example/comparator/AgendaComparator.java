package org.example.comparator;

import java.util.Comparator;
import java.util.List;
import org.example.entity.Agenda;
import org.example.entity.Situacao;

public class AgendaComparator implements Comparator<Agenda> {
    @Override
    public int compare(Agenda agenda1, Agenda agenda2) {
        // Define a ordem desejada
        List<Situacao> ordemDesejada = List.of(Situacao.AGENDADO, Situacao.REALIZADO, Situacao.CANCELADO);

        // Obtém as posições dos estados na ordem desejada
        int posicao1 = ordemDesejada.indexOf(agenda1.getSituacao());
        int posicao2 = ordemDesejada.indexOf(agenda2.getSituacao());

        // Compara as posições
        return Integer.compare(posicao1, posicao2);
    }
}
