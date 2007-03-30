require 'net/http'
require 'rexml/document'

require 'team.rb'

module MindquarryTalk

  class Server

    attr_reader :http

    def initialize(server, port, username, password)
      @server = server
      @port = port
      @username = username
      @password = password
  
      @http = Net::HTTP.new(@server, @port)
    end

    def getRequestForPath(path = '/')
      # puts "GET  #{path}"
      request = Net::HTTP::Get.new(path)
      request["accept"] = "text/xml"
      request.basic_auth @username, @password
      return request
    end

    def putRequestForPath(path = '/')
      # puts "POST #{path}"
      request = Net::HTTP::Put.new(path)
      request.basic_auth @username, @password
      return request
    end
    
    def getTeams
      teams = []
      http.start do |http|
        request = getRequestForPath "/teams/"
        response = http.request(request)
        doc = REXML::Document.new(response.body)
        doc.root.each_element do |node|
          id = node.attribute('xlink:href').value
          teams.push Team.new(self, id)
        end
      end
      teams.each do |team|
        team.getMeta
      end
      return teams
    end
  end

end
