library(rvest)
library(dplyr)
library(RCurl)
#       New Releases
fetch_new_releases <- function(){
  all_games_link <- "https://www.metacritic.com/browse/games/score/metascore/all/pc/filtered?page=1"
  page <- read_html(all_games_link)
  all_games_list2 <- page %>% 
    html_nodes(".title h3") %>% 
    html_text()
  
  games <- data.frame(matrix(ncol=5, nrow=0))
  col_names <- c("name", "point", "summary", "dev", "genre")
  colnames(games) <- col_names
  
  get_links <- function(game_link){
    game_link <- game_link %>% 
      gsub(" ", "-", ., fixed = T) %>%
      gsub(":", "", ., fixed = T) %>%
      gsub("'", "", ., fixed = T) %>% 
      tolower()
    
    game_page <- paste("https://www.metacritic.com/game/pc/",game_link, sep = "")
    return(game_page)
  }
  links1 <- sapply(all_games_list1, get_links, USE.NAMES = F)
  links2 <- sapply(all_games_list2, get_links, USE.NAMES = F)
  
  get_game_features <- function(link){
    print(link)
    game_link <- read_html(link)
    features <- game_link %>% 
      html_nodes(".product_summary .data span , .product_genre .label+ .data , .button , .positive span , h1") %>% 
      html_text()
    if(length(features) > 5){
      features <- features[c(1, 2, 5, 8, 9)]
    }
    games[nrow(games) + 1,] =  features
    return(games)
  }
  
  links <- c("https://www.metacritic.com/game/pc/disco-elysium-the-final-cut", 
             "https://www.metacritic.com/game/pc/forza-horizon-5",
             "https://www.metacritic.com/game/pc/chicory-a-colorful-tale")
  
  links <- c(links1,links2)
  games <- t(sapply(links, get_game_features, USE.NAMES = F))
  
  games_df <- as.data.frame(games) %>% 
    apply( 2, as.character)
  
  games_df_2 <- read.csv("best_games_data.csv")
  
  write.csv(games_df,"best_games_data.csv")
}




#       images for new releases
fetch_images <- function(){
  img_src1 <- read_html("https://www.metacritic.com/browse/games/score/metascore/all/pc/filtered") %>% 
    html_nodes(".clamp-image-wrap a img") %>% 
    html_attr("src")
  
  img_src2 <- read_html("https://www.metacritic.com/browse/games/score/metascore/all/pc/filtered?page=1") %>% 
    html_nodes(".clamp-image-wrap a img") %>% 
    html_attr("src")
  
  img_src <- c(img_src1, img_src2)
  
  all_games_list1 <- all_games_list1 %>% 
    gsub(" ", "-", ., fixed = T) %>%
    gsub(":", "", ., fixed = T) %>%
    gsub("'", "", ., fixed = T) %>% 
    tolower()
  
  all_games_list2 <- all_games_list2 %>% 
    gsub(" ", "-", ., fixed = T) %>%
    gsub(":", "", ., fixed = T) %>%
    gsub("'", "", ., fixed = T) %>% 
    tolower()
  
  all <- c(all_games_list1, all_games_list2)
  x <- c(1:200)
  for(i in x){
    download.file(img_src[i], paste("./best_games_images/",all[i], ".jpg" ), mode = "wb")
  }
}




df <- read.csv("best_games_data.csv")
df <- df[2:6]

#name
  df[195,1] <- "Streets of Rage 4: Mr. X Nightmare"
  
#points
  df[195,2] <-88

#summary
  df[195,3] <- "The fight continues in Wood Oak City. After the events of Streets of Rage 4, our heroes wanted to prepare themselves for future threats. Axel, Blaze and their mates will start a very special deranged training with the help of Dr. Zan, who built an AI program from the remnants of Mister X’s brain that simulates every kind of danger they could be facing.
With this new DLC, get ready for:
• 3 new playable characters
• A new Survival mode with weekly challenges
• Character customization: build your own fighting style with new moves
• New weapons and enemies!"
  
#dev
  df[195,4] <- "Lizardcube"
  
#genre
  df[195,5] <- "Action"
  
  
  write.csv(df, "best_games_data.csv")



  