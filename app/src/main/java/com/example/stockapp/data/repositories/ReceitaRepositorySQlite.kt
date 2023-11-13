package com.example.stockapp.data.repositories

import android.util.Log
import com.example.stockapp.data.objects.Receita
import com.example.stockapp.data.daos.ReceitaDao
import com.example.stockapp.data.objects.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReceitaRepositorySQlite
@Inject constructor(private val receitaDao: ReceitaDao)
    : ReceitaRepository {

    override  val receitas: Flow<List<Receita>>
        get() = receitaDao.list()

    override suspend fun set(receita: Receita) {
        if (receita.id == 0){
            receitaDao.set(receita)
        } else {
            receitaDao.update(receita)
        }
    }

    override suspend fun delete(id: Int){
        receitaDao.delete(id)
    }

    init {
        CoroutineScope(Job()).launch {
            receitaDao.deleteAll()
            Log.i("Receita", "-----------> Limpou a base de dados!")
            //delay(15000)
            val receitas = receitas()
            for(r in receitas){
                receitaDao.set(r)
            }
            Log.i("Receita", "-----------> Inseriu dados na base!")
        }

    }

    companion object {
        fun receitas(): MutableList<Receita> {
            val lista = mutableListOf(
                Receita(
                    1,
                    "1",
                    "Risoto de Cogumelos",
                    "2 xícaras de arroz arbóreo \n" +
                            "200g de cogumelos (shiitake, shimeji, champignon) \n" +
                            "1 cebola picada \n" +
                            "2 dentes de alho picados \n" +
                            "1/2 xícara de vinho branco \n" +
                            "4 xícaras de caldo de legumes \n" +
                            "2 colheres de sopa de manteiga \n" +
                            "1/2 xícara de queijo parmesão ralado \n" +
                            "Sal e pimenta a gosto \n",
                    "1. Em uma panela, refogue a cebola e o alho em um pouco de azeite até ficarem transparentes. \n" +
                            "2. Adicione os cogumelos e cozinhe até que liberem água e fiquem dourados. \n" +
                            "3. Acrescente o arroz e mexa por alguns minutos. \n" +
                            "4. Despeje o vinho branco e mexa até evaporar. \n" +
                            "5. Adicione o caldo de legumes aos poucos, mexendo constantemente, até que o arroz esteja cozido. \n" +
                            "6. Misture a manteiga e o queijo parmesão. \n" +
                            "7. Tempere com sal e pimenta. \n" +
                            "8. Sirva quente. \n",
                    350,
                    0,
                    "risotocogumelos",
                    1
                ),

                Receita(
                    2,
                    "2",
                    "Bolo de banana invertido",
                    "7 bananas bem maduras para a massa\n" +
                            "\n" +
                            "4 ovos\n" +
                            "\n" +
                            "1/2 xícara de eritritol ou açúcar mascavo ou demerara\n" +
                            "\n" +
                            "1/2 xícara de óleo de girassol ou de coco\n" +
                            "\n" +
                            "1 colher de sopa de canela\n" +
                            "\n" +
                            "1 xícara de farelo de aveia\n" +
                            "\n" +
                            "1/2 xícara de farinha de arroz\n" +
                            "\n" +
                            "1/2 xícara de farinha de amêndoas\n" +
                            "\n" +
                            "1 colher de sopa de fermento",
                    "Test",
                    200,
                    0,
                    "semfoto.jpg",
                     2
                ),

                Receita(
                    3,
                    "3",
                    "Bolo de fubá sem lactose e sem soja",
                   "3 ovos \n " + "1 xícara de óleo de canola" + "\n" +
                           "1 xícara de fubá \n" +
                            "1 pitada de sal \n" +
                           "2 colheres de fermento em po químico \n" +
                            "1 xícara de açúcar \n" +
                            "1 xícara de água \n" +
                            "1 xícara e meia de farinha de trigo \n" +
                            "50 g de coco ralado \n",

                    "1. Bata os ovos com o açúcar até virar uma espuma. \n" +
                            "2. Acrescente o óleo, em seguida a água e continue batendo. \n" +
                            "3. Acrescente o fubá e a farinha de trigo, o sal, pare de bater e com calma misture o coco e fermento em pó. \n" +
                            "4. Asse em uma forma untada com óleo de canola.",
                    210,
                    0,
                    "bolofuba",
                    3
                ),

                Receita(
                    4,
                    "4",
                    "Pão de banana sem glúten",
                    "3 bananas maduras \n" +
                            "3 ovos \n" +
                            "1/2 xícara de farinha de amêndoas \n" +
                            "1/2 xícara de farinha de coco \n" +
                            "1 colher de chá de fermento em pó \n" +
                            "1/2 colher de chá de canela \n" +
                            "1/4 de xícara de nozes picadas \n",
                    "1. Pré-aqueça o forno a 180°C e unte uma forma de pão. \n" +
                            "2. Em uma tigela, amasse as bananas e misture com os ovos. \n" +
                            "3. Adicione as farinhas, o fermento e a canela e misture bem. \n" +
                            "4. Acrescente as nozes picadas. \n" +
                            "5. Despeje a massa na forma e asse por cerca de 40 minutos. \n" +
                            "6. Deixe esfriar antes de cortar em fatias. \n",
                    180,
                    0,
                    "paobanana",
                    3
                ),

                Receita(
                        5,
                    "5",
                "Panquecas de aveia sem glúten",
                "1 xícara de aveia em flocos \n" +
                        "1/2 xícara de leite de amêndoa \n" +
                        "1 banana madura \n" +
                        "1 ovo \n" +
                        "1 colher de chá de fermento em pó \n" +
                        "1/2 colher de chá de canela \n",
                "1. Coloque a aveia, o leite de amêndoa, a banana, o ovo, o fermento e a canela no liquidificador e bata até obter uma massa homogênea. \n" +
                        "2. Aqueça uma frigideira antiaderente e despeje conchas de massa para formar as panquecas. \n" +
                        "3. Cozinhe até que bolhas apareçam na superfície e vire as panquecas para dourar o outro lado. \n" +
                        "4. Sirva com frutas frescas e xarope de bordo. \n",
                220,
                0,
                "panquecasaveia",
                3
            ),
                Receita(
                    6,
                    "6",
                    "Torta de frango sem glúten",
                    "Para a massa: \n" +
                            "2 xícaras de farinha de arroz \n" +
                            "1/2 xícara de amido de milho \n" +
                            "1 colher de chá de sal \n" +
                            "1/2 xícara de óleo vegetal \n" +
                            "1/2 xícara de água gelada \n" +
                            "Para o recheio: \n" +
                            "2 xícaras de peito de frango cozido e desfiado \n" +
                            "1 cebola picada \n" +
                            "2 dentes de alho picados \n" +
                            "1 cenoura ralada \n" +
                            "1 xícara de ervilhas \n" +
                            "1 xícara de leite de amêndoa \n" +
                            "2 colheres de sopa de farinha de arroz \n" +
                            "Sal e pimenta a gosto \n",
                    "Para a massa: \n" +
                            "1. Misture a farinha de arroz, o amido de milho e o sal em uma tigela. \n" +
                            "2. Adicione o óleo e a água gelada e misture até formar uma massa. \n" +
                            "3. Abra a massa em uma forma de torta untada. \n" +
                            "Para o recheio: \n" +
                            "1. Refogue a cebola e o alho em um pouco de óleo. \n" +
                            "2. Adicione o frango desfiado, a cenoura e as ervilhas. \n" +
                            "3. Em outra panela, misture o leite de amêndoa e a farinha de arroz até engrossar. \n" +
                            "4. Tempere com sal e pimenta. \n" +
                            "5. Despeje o recheio na massa da torta e cubra com a massa restante. \n" +
                            "6. Asse a torta a 180°C por cerca de 30 minutos, ou até dourar. \n",
                    350,
                    0,
                    "tortafrango",
                    3
                ),

                Receita(
                    7,
                    "7",
                    "Brownie de batata doce sem glúten",
                    "2 xícaras de batata doce cozida e amassada \n" +
                            "1/2 xícara de farinha de amêndoas \n" +
                            "1/4 de xícara de cacau em pó \n" +
                            "1/4 de xícara de mel ou xarope de bordo \n" +
                            "2 ovos \n" +
                            "1 colher de chá de extrato de baunilha \n" +
                            "1/2 colher de chá de fermento em pó \n" +
                            "1/4 de colher de chá de sal \n",
                    "1. Pré-aqueça o forno a 180°C e unte uma forma. \n" +
                            "2. Em uma tigela, misture a batata doce, a farinha de amêndoas, o cacau em pó, o mel (ou xarope de bordo), os ovos, a baunilha, o fermento e o sal. \n" +
                            "3. Despeje a massa na forma e espalhe uniformemente. \n" +
                            "4. Asse por cerca de 25-30 minutos, ou até que um palito saia limpo. \n" +
                            "5. Deixe esfriar antes de cortar em pedaços. \n",
                    180,
                    0,
                    "browniebatatadoce",
                    3
                ),

                Receita(
                    8,
                    "8",
                    "Salada de Quinoa com Abacate",
                    "1 xícara de quinoa \n" +
                            "2 xícaras de água \n" +
                            "1 abacate maduro \n" +
                            "1/2 xícara de tomate cereja \n" +
                            "1/4 de xícara de coentro picado \n" +
                            "Suco de 1 limão \n" +
                            "Sal e pimenta a gosto \n",
                    "1. Enxágue a quinoa em água corrente. \n" +
                            "2. Cozinhe a quinoa em duas xícaras de água até que a água seja absorvida. \n" +
                            "3. Em uma tigela, misture a quinoa cozida, o abacate, os tomates cereja, o coentro, o suco de limão, o sal e a pimenta. \n" +
                            "4. Sirva a salada fria. \n",
                    220,
                    0,
                    "saladaquinoa",
                    1
                ),

                Receita(
                    9,
                    "9",
                    "Curry de Grão-de-bico",
                    "2 latas de grão-de-bico \n" +
                            "1 cebola picada \n" +
                            "2 dentes de alho picados \n" +
                            "1 colher de chá de gengibre fresco ralado \n" +
                            "2 colheres de sopa de curry em pó \n" +
                            "1 lata de tomates picados \n" +
                            "1 lata de leite de coco \n" +
                            "Sal e pimenta a gosto \n",
                    "1. Em uma panela grande, refogue a cebola, o alho e o gengibre em um pouco de óleo. \n" +
                            "2. Adicione o curry em pó e refogue por mais alguns minutos. \n" +
                            "3. Acrescente os tomates picados, o grão-de-bico e o leite de coco. \n" +
                            "4. Cozinhe por cerca de 15 minutos. \n" +
                            "5. Tempere com sal e pimenta. \n" +
                            "6. Sirva com arroz ou pão naan. \n",
                    280,
                    0,
                    "currygraodebico",
                    1
                ),
                Receita(
                    10,
                    "10",
                    "Lasanha de Berinjela",
                    "2 berinjelas grandes \n" +
                            "1 xícara de queijo ricota \n" +
                            "1/2 xícara de queijo parmesão ralado \n" +
                            "1 xícara de molho de tomate \n" +
                            "Manjericão fresco a gosto \n" +
                            "Sal e pimenta a gosto \n",
                    "1. Corte as berinjelas em fatias finas e grelhe até que fiquem macias. \n" +
                            "2. Em uma tigela, misture a ricota, metade do queijo parmesão, o manjericão, o sal e a pimenta. \n" +
                            "3. Em um refratário, faça camadas alternadas de berinjela grelhada, molho de tomate e a mistura de ricota. \n" +
                            "4. Polvilhe o restante do queijo parmesão por cima. \n" +
                            "5. Asse no forno a 180°C por cerca de 30 minutos, ou até que o queijo esteja dourado. \n",
                    350,
                    0,
                    "lasanhaberinjela",
                    1
                ),
                Receita(
                    11,
                    "11",
                    "Hambúrguer de Feijão Preto",
                    "2 xícaras de feijão preto cozido \n" +
                            "1/2 xícara de aveia em flocos \n" +
                            "1/2 cebola picada \n" +
                            "2 dentes de alho picados \n" +
                            "1 colher de chá de cominho \n" +
                            "1 colher de chá de páprica defumada \n" +
                            "Sal e pimenta a gosto \n",
                    "1. Em um processador de alimentos, misture o feijão preto, a aveia, a cebola, o alho, o cominho, a páprica, o sal e a pimenta. \n" +
                            "2. Forme hambúrgueres com a mistura. \n" +
                            "3. Grelhe ou asse os hambúrgueres até que fiquem dourados dos dois lados. \n" +
                            "4. Sirva em pães de hambúrguer com seus acompanhamentos favoritos. \n",
                    200,
                    0,
                    "hamburguerfeijaopreto",
                    1
                ),

                Receita(
                    12,
                    "12",
                    "Tacos de Couve-Flor",
                    "1 couve-flor média \n" +
                            "2 colheres de sopa de azeite de oliva \n" +
                            "1 colher de chá de cominho \n" +
                            "1 colher de chá de páprica defumada \n" +
                            "Sal e pimenta a gosto \n" +
                            "Tortilhas de milho \n" +
                            "Abacate, salsa, e coentro para servir \n",
                    "1. Corte a couve-flor em floretes. \n" +
                            "2. Em uma tigela, misture os floretes de couve-flor com o azeite, o cominho, a páprica, o sal e a pimenta. \n" +
                            "3. Asse a couve-flor em um forno a 200°C até que fique macia e dourada. \n" +
                            "4. Monta os tacos com a couve-flor assada, abacate, salsa e coentro em tortilhas de milho. \n",
                    240,
                    0,
                    "tacoscouveflor",
                    1
                ),

                Receita(
                    13,
                    "13",
                    "Salada de Abacate e Quinoa",
                    "1 xícara de quinoa \n" +
                            "2 xícaras de água \n" +
                            "1 abacate maduro \n" +
                            "1/2 xícara de tomate cereja \n" +
                            "1/4 de xícara de coentro picado \n" +
                            "Suco de 1 limão \n" +
                            "Sal e pimenta a gosto \n",
                    "1. Enxágue a quinoa em água corrente. \n" +
                            "2. Cozinhe a quinoa em duas xícaras de água até que a água seja absorvida. \n" +
                            "3. Em uma tigela, misture a quinoa cozida, o abacate, os tomates cereja, o coentro, o suco de limão, o sal e a pimenta. \n" +
                            "4. Sirva a salada fria. \n",
                    220,
                    0,
                    "saladaabacatequinoa",
                    1
                )

            )

            return lista
        }
    }

}

