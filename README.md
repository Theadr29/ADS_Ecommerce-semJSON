# ADS_Ecommerce-semJSON
Meu primeiro aplicativo usei conhecimento basico, hoje estou mais evoluido, aprendi a usar fragments.. outros projetos virão!
O codigo de todo aplicativo esta sem o JSON por motivos de segurança
As variaveis e as "keys" se baseiam em assuntos relacionados a pisos pois a ideia era somente vender pisos, porem se expandiu 
e mudar afetaria o banco de dados e a velocidade da entrega que o cliente queria... eu decidi pela entrega mais irei refatora-lo

Tecnologias usadas:

Splash art usando o Handle(3 segundos)
Goggle singIN Api para loguin.
Navigation Drawer menu.
Glide para importar,e orientar o tamanho e resolução de imagens.
Banco de dados SQLite(ROOM DataBase).
Data class para alimentar o banco de dados.
DAO para fazer o CRUD usando operações SQL, varias operaçoes foram feitas, como adição subtração e exlusão total de itens apos o usuario clicar em sair do usuario.
RecyclerView para visualizar os dados do banco de dados.
Adapter para fornecer as visualizações para a recycler View.
Usei a class ItoucheHelper e a função onSwiped para arrastar os itens atrelando isso ao adapter,recycler view e a minha DAO.
assim excluindo os itens na minha recycler View de forma mais "sofisticada".
Coroutines (Coroutines é vida!).
Tambem usei as bibliotecas Canvas e Paint para criar o orçamento do usuario em uma imagem Jpg.
Para isso tive que criar um layout e inserir todos os dados e formata-los na imagem.
Criei um intent que apos o processamento e geração da imagem, envia a propria para o wattsapp(não para um numero expecifico, ainda)

Para acessar o app sem Login é somente excluir a LoginActivity e colocar a Main Activity como principal
Exclua os codigos Gson do google sing in na CupomDeCompraA ctivity
